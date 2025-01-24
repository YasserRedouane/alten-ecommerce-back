package com.alten.ecommerce.controllers;

import com.alten.ecommerce.dtos.requests.ProductRequest;
import com.alten.ecommerce.dtos.responses.ApiResponse;
import com.alten.ecommerce.dtos.responses.ProductResponse;
import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Validated ProductRequest product,
                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(productService.createProduct(product, token));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody ProductRequest productRequest,
                                                         @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest, token));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String token) {
        productService.deleteProduct(id, token);
        ApiResponse response = new ApiResponse("Product successfully deleted with ID: " + id, 200);
        return ResponseEntity.ok(response);
    }

}
