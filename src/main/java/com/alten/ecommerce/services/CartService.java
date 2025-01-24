package com.alten.ecommerce.services;

import com.alten.ecommerce.dtos.requests.CartItemRequest;
import com.alten.ecommerce.dtos.requests.CartRequest;
import com.alten.ecommerce.dtos.responses.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse getCartByUserId(Long userId);

    CartResponse addProductToCart(Long userId, CartRequest cartItemRequest);

    CartResponse removeProductFromCart(Long userId, Long productId);

    List<CartResponse> getAllCarts();

    CartResponse updateProductQuantity(Long userId, Long productId, Integer quantity);
}
