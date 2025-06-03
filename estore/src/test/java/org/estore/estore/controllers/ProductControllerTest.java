package org.estore.estore.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCanAddProduct() throws Exception {
        String imagePath = "C:\\Users\\Dell\\OneDrive\\Desktop\\e-store\\byte_builder_store\\estore\\src\\test\\resources\\assests\\sayan-majhi-uVb2BMH0PIY-unsplash.jpg";
        Path image = Paths.get(imagePath);
        mockMvc.perform(multipart("/api/v1/product")
                        .file("media",new MockMultipartFile("image", Files.newInputStream(image)).getBytes())
                        .params(buildFormFields())
                        .with(request ->{
                            request.setMethod(PUT.name());
                            request.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
                            return request;
                        })
                ).andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    private static  MultiValueMap<String,String> buildFormFields(){
        MultiValueMap<String,String> fields = new LinkedMultiValueMap<>();
        fields.add("name", "Iphone");
        fields.add("price", BigDecimal.valueOf(1000).toString());
        fields.add("description", "Iphone 16 pro max");
        fields.add("quantity", "10");

        return fields;
    }
}
