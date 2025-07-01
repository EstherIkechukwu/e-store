package org.estore.estore.integrations;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.estore.estore.dto.response.walrus.WalrusUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.PUT;

@Component
@RequiredArgsConstructor
public class WalrusCloudService implements CloudService {

    @Value("${walrus.app.url}")
    private String walrusUrl;

    @Value("${walrus.app.aggregator}")
    private String walrusAggregator;

    @Value("${walrus.app.epoch}")
    private String epoch;

    @Value("${walrus.app.address}")
    private String walrusUploadAddress;

    private final RestTemplate restTemplate;


    @Override
    public String upload(MultipartFile file){
        return extractBlobIdFrom(restTemplate.exchange(walrusUrl, PUT,
                buildUploadRequest(file), WalrusUploadResponse.class,
                createQueryParams()));
    }

    @Override
    public byte[] getFileBy(String blobId){
        ResponseEntity<byte[]> response = restTemplate.getForEntity(walrusAggregator.concat(blobId), byte[].class);
        return response.getBody();
    }

    private Map<String, ?> createQueryParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("epochs", Integer.parseInt(epoch));
        params.put("send_object_to", walrusUploadAddress);
        return params;
    }

    public HttpEntity<?> buildUploadRequest(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        Resource resource = file.getResource();
        return new HttpEntity<>(resource, headers);
    }

    private static String extractBlobIdFrom(ResponseEntity<WalrusUploadResponse> response) {
        WalrusUploadResponse walrusUploadResponse = response.getBody();
        boolean isFileAlreadyExists = walrusUploadResponse != null && walrusUploadResponse.getNewlyCreated() == null;
        if (isFileAlreadyExists) {
            return walrusUploadResponse.getAlreadyCertified().getBlobId();
        }
        return walrusUploadResponse.getNewlyCreated().getBlobObject().getBlobId();
    }
}
