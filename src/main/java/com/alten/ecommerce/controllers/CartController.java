package com.alten.ecommerce.controllers;

import com.alten.ecommerce.dtos.requests.CartRequest;
import com.alten.ecommerce.dtos.responses.CartResponse;
import com.alten.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Endpoint to get the cart for a user
    @GetMapping
    public CartResponse getCart(@RequestParam Long userId) {
        return cartService.getCartByUserId(userId);
    }

    // Endpoint to add a product to the cart
    @PostMapping("/add")
    public CartResponse addProductToCart(@RequestParam Long userId, @RequestBody CartRequest cartRequest) {
        return cartService.addProductToCart(userId, cartRequest);
    }

    // Endpoint to remove a product from the cart
    @DeleteMapping("/remove")
    public void removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeProductFromCart(userId, productId);
    }

    // Endpoint to update the quantity of a product in the cart
    @PutMapping("/update")
    public CartResponse updateProductQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity) {
        return cartService.updateProductQuantity(userId, productId, quantity);
    }
}
