package com.alten.ecommerce.dtos.requests;

import com.alten.ecommerce.entities.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItemRequest {
    private Cart cart;
    private Long productId;
    private Integer quantity;
}
