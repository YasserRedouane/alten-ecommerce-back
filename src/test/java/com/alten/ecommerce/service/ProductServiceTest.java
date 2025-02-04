package com.alten.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.alten.ecommerce.dtos.mappers.ProductMapper;
import com.alten.ecommerce.dtos.requests.ProductRequest;
import com.alten.ecommerce.dtos.responses.ProductResponse;
import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.enums.InventoryStatus;
import com.alten.ecommerce.exceptions.UnauthorizedAccessException;
import com.alten.ecommerce.repositories.ProductRepository;
import com.alten.ecommerce.services.ProductService;
import com.alten.ecommerce.services.impl.ProductServiceImpl;
import com.alten.ecommerce.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String VALID_TOKEN = "Bearer valid_token";
    private static final String INVALID_TOKEN = "Bearer invalid_token";

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    private ProductRequest productRequest;
    private Product product;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, productMapper, jwtUtil);
        productRequest = ProductRequest.builder()
                .code("P001")
                .name("Product 1")
                .description("A test product")
                .image("http://example.com/image.jpg")
                .category("Category 1")
                .price(100.0)
                .quantity(10)
                .internalReference("INT001")
                .shellId(1)
                .inventoryStatus(InventoryStatus.INSTOCK)
                .rating(4.5)
                .build();

        product = Product.builder()
                .id(1L)
                .code("P001")
                .name("Product 1")
                .description("A test product")
                .image("http://example.com/image.jpg")
                .category("Category 1")
                .price(100.0)
                .quantity(10)
                .internalReference("INT001")
                .shellId(1)
                .inventoryStatus(InventoryStatus.INSTOCK)
                .rating(4.5)
                .build();

        productResponse = ProductResponse.builder()
                .code("P001")
                .name("Product 1")
                .description("A test product")
                .image("http://example.com/image.jpg")
                .category("Category 1")
                .price(100.0)
                .quantity(10)
                .internalReference("INT001")
                .shellId(1)
                .inventoryStatus(InventoryStatus.INSTOCK)
                .rating(4.5)
                .build();
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        when(jwtUtil.extractEmail("valid_token")).thenReturn(ADMIN_EMAIL);


        when(productMapper.fromRequestToEntity(productRequest)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.fromEntityToResponse(product)).thenReturn(productResponse);

        // Act
        ProductResponse response = productService.createProduct(productRequest, VALID_TOKEN);

        // Assert
        assertNotNull(response);
        assertEquals(productResponse, response);


        verify(jwtUtil).extractEmail("valid_token");
        verify(productMapper).fromRequestToEntity(productRequest);
        verify(productRepository).save(product);
        verify(productMapper).fromEntityToResponse(product);
    }

    @Test
    void testCreateProduct_UnauthorizedAccess() {
        // Arrange
        when(jwtUtil.extractEmail("invalid_token")).thenReturn("user@example.com");

        // Act
        UnauthorizedAccessException exception = assertThrows(
                UnauthorizedAccessException.class,
                () -> productService.createProduct(productRequest, INVALID_TOKEN)
        );

        // Assert
        assertEquals("Only admin can create products.", exception.getMessage());

        // Ensure product creation does not proceed
        verify(productMapper, never()).fromRequestToEntity(any());
        verify(productRepository, never()).save(any());
    }
}
