package com.alten.ecommerce.services;

import com.alten.ecommerce.dtos.requests.ProductRequest;
import com.alten.ecommerce.dtos.responses.ProductResponse;
import com.alten.ecommerce.entities.Product;


import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest product, String token);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest product, String token);

    void deleteProduct(Long id, String token);
}
