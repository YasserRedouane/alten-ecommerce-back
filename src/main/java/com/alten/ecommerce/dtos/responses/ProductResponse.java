package com.alten.ecommerce.dtos.responses;

import com.alten.ecommerce.enums.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private Double price;
    private Integer quantity;
    private String internalReference;
    private Integer shellId;
    private InventoryStatus inventoryStatus;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
