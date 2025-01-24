package com.alten.ecommerce.services.impl;

import com.alten.ecommerce.dtos.mappers.ProductMapper;
import com.alten.ecommerce.dtos.requests.ProductRequest;
import com.alten.ecommerce.dtos.responses.ProductResponse;
import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.exceptions.ResourceNotFoundException;
import com.alten.ecommerce.exceptions.UnauthorizedAccessException;
import com.alten.ecommerce.repositories.ProductRepository;
import com.alten.ecommerce.services.ProductService;
import com.alten.ecommerce.utils.JwtUtil;
import com.alten.ecommerce.utils.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_EMAIL = "admin@admin.com";
    @Override
    public ProductResponse createProduct(ProductRequest productRequest, String token) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        if (!ADMIN_EMAIL.equals(email)) {
            throw new UnauthorizedAccessException("Only admin can create products.");
        }

        Product product = productMapper.fromRequestToEntity(productRequest);
        ProductValidator.validateProduct(product, false);
        Product savedProduct = productRepository.save(product);
        return productMapper.fromEntityToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return productMapper.fromEntityToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest, String token) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        if (!ADMIN_EMAIL.equals(email)) {
            throw new UnauthorizedAccessException("Only admin can update products.");
        }

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        Product updatedProduct = productMapper.fromRequestToEntity(productRequest);
        updatedProduct.setId(existingProduct.getId());
        ProductValidator.validateProduct(updatedProduct, true);

        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.fromEntityToResponse(savedProduct);
    }


    @Override
    public void deleteProduct(Long id, String token) {

        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        if (!ADMIN_EMAIL.equals(email)) {
            throw new UnauthorizedAccessException("Only admin can delete products.");
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }
}
