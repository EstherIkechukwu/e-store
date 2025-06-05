package org.estore.estore.service;

import org.estore.estore.dto.request.AddProductRequest;
import org.estore.estore.dto.response.AddProductResponse;
import org.estore.estore.model.Product;

import java.math.BigDecimal;

public interface ProductService {
    Product getProductBy(String id);
    AddProductResponse add(AddProductRequest productRequest);

    Product updateProductQuantity(String id, Long newQuantity);

    void deleteProduct(String id);

    Product updateProductPrice(String id, BigDecimal newPrice);
}
