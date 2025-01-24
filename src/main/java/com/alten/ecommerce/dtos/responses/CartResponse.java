package com.alten.ecommerce.dtos.responses;

import com.alten.ecommerce.entities.CartItem;
import com.alten.ecommerce.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private Long cartId;
    private Long userId;
    private List<CartItemResponse> cartItems;
    private Double totalPrice;
}
