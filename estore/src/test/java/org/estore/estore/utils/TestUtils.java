package org.estore.estore.utils;

import org.estore.estore.dto.request.AddProductRequest;
import org.estore.estore.dto.request.CreateOrderRequest;
import org.estore.estore.dto.request.ItemRequest;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TestUtils {

    public static CreateOrderRequest buildCreateOrder() {
        CreateOrderRequest order = new CreateOrderRequest();
        order.setItems(List.of(new ItemRequest("26943268-c87b-4d41-af5e-2cc83a6d2bc8", 2),
                new ItemRequest("1c7469de-63ec-4299-a160-3e03d3a2e3c8", 5)));
        order.setCartId("7cfbabc9-92f6-427d-b2b0-6acf84aaa881");
        return order;
    }

    public static AddProductRequest buildAddProduct() {
        String imagePath = "C:\\Users\\Dell\\OneDrive\\Desktop\\e-store\\estore\\src\\test\\resources\\assests\\sayan-majhi-uVb2BMH0PIY-unsplash.jpg";
        Path image = Paths.get(imagePath);

        try(var imageStream = Files.newInputStream(image);){
            AddProductRequest productRequest = new AddProductRequest();
            productRequest.setName("iphone 17");
            productRequest.setMedia(
                    List.of(
                            new MockMultipartFile("media", imageStream)));
            productRequest.setDescription("iphone 17");
            productRequest.setPrice(BigDecimal.valueOf(2_000_000));
            return productRequest;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
