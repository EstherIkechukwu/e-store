package org.estore.estore.integrations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalrusCloudServiceTest {
    @Autowired
    private WalrusCloudService walrusCloudService;

    @Test
    void testCanUploadFile(){
        String fileLocation = "C:\\Users\\Dell\\OneDrive\\Desktop\\e-store\\byte_builder_store\\estore\\src\\test\\resources\\assests\\sayan-majhi-uVb2BMH0PIY-unsplash.jpg";
        Path path = Paths.get(fileLocation);
        try(var inputStream = Files.newInputStream(path)){
            MultipartFile file = new MockMultipartFile("image", inputStream);
            String blodId = walrusCloudService.upload(file);
            assertThat(blodId).isNotNull();
            assertThat(blodId).isNotEmpty();
        }
        catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
