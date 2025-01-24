package com.alten.ecommerce.utils;

import com.alten.ecommerce.entities.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductValidator {

    public static void validateProduct(Product product, boolean isUpdate) {
        if (Objects.isNull(product)) {
            throw new IllegalArgumentException("Product object cannot be null.");
        }

        if (!isUpdate && (product.getCode() == null || product.getCode().isBlank())) {
            throw new IllegalArgumentException("Code cannot be null or blank during creation.");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank.");
        }

        if (product.getDescription() == null || product.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank.");
        }

        if (product.getCategory() == null || product.getCategory().isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank.");
        }

        if (product.getInventoryStatus() == null) {
            throw new IllegalArgumentException("Inventory status cannot be null.");
        }

        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }

        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Price must be a positive value.");
        }
    }
}
