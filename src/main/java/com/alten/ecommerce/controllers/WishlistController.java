package com.alten.ecommerce.controllers;

import com.alten.ecommerce.dtos.requests.WishlistItemRequest;
import com.alten.ecommerce.dtos.responses.WishlistResponse;
import com.alten.ecommerce.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // Endpoint to get the wishlist for a user
    @GetMapping
    public WishlistResponse getWishlist(@RequestParam Long userId) {
        return wishlistService.getWishlistByUserId(userId);
    }

    // Endpoint to add a product to the wishlist
    @PostMapping("/add")
    public WishlistResponse addProductToWishlist(@RequestParam Long userId, @RequestBody WishlistItemRequest wishlistRequest) {
        return wishlistService.addProductToWishlist(userId, wishlistRequest);
    }

    // Endpoint to remove a product from the wishlist
    @DeleteMapping("/remove")
    public void removeProductFromWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        wishlistService.removeProductFromWishlist(userId, productId);
    }
}
