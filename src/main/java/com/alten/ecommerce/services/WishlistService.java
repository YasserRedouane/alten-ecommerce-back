package com.alten.ecommerce.services;

import com.alten.ecommerce.dtos.requests.WishlistItemRequest;
import com.alten.ecommerce.dtos.responses.WishlistResponse;

import java.util.List;

public interface WishlistService {
    WishlistResponse getWishlistByUserId(Long userId);
    WishlistResponse addProductToWishlist(Long userId, WishlistItemRequest wishlistItemRequest);
    WishlistResponse removeProductFromWishlist(Long userId, Long productId);
    List<WishlistResponse> getAllWishlists();
}
