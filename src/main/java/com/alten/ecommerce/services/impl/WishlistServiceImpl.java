package com.alten.ecommerce.services.impl;

import com.alten.ecommerce.dtos.mappers.WishlistMapper;
import com.alten.ecommerce.dtos.requests.WishlistItemRequest;
import com.alten.ecommerce.dtos.responses.WishlistResponse;
import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.entities.User;
import com.alten.ecommerce.entities.Wishlist;
import com.alten.ecommerce.entities.WishlistItem;
import com.alten.ecommerce.exceptions.ResourceNotFoundException;
import com.alten.ecommerce.repositories.ProductRepository;
import com.alten.ecommerce.repositories.UserRepository;
import com.alten.ecommerce.repositories.WishlistItemRepository;
import com.alten.ecommerce.repositories.WishlistRepository;
import com.alten.ecommerce.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    @Override
    public WishlistResponse getWishlistByUserId(Long userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found for user ID: " + userId));

        return wishlistMapper.fromEntityToResponse(wishlist);
    }

    @Override
    @Transactional
    public WishlistResponse addProductToWishlist(Long userId, WishlistItemRequest wishlistItemRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Product product = productRepository.findById(wishlistItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + wishlistItemRequest.getProductId()));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseGet(() -> wishlistRepository.save(Wishlist.builder().user(user).build()));

        // Ensure wishlist items list is initialized
        if (wishlist.getItems() == null) {
            wishlist.setItems(new ArrayList<>());
        }

        // Check if the product already exists in the wishlist
        WishlistItem existingItem = wishlist.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            // Increment the quantity and update the totalItemPrice
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            wishlistItemRepository.save(existingItem);
        } else {
            // Add a new item to the wishlist
            WishlistItem wishlistItem = WishlistItem.builder()
                    .wishlist(wishlist)
                    .product(product)
                    .quantity(1)
                    .build();
            WishlistItem savedWishlistItem = wishlistItemRepository.save(wishlistItem);

            wishlist.getItems().add(savedWishlistItem);
        }

        wishlistRepository.save(wishlist);

        return wishlistMapper.fromEntityToResponse(wishlist);
    }


    @Override
    public WishlistResponse removeProductFromWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wishlist not found for user ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        WishlistItem wishlistItem = wishlistItemRepository.findByWishlistAndProduct(wishlist, product)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist"));

        wishlist.getItems().remove(wishlistItem);
        wishlistItemRepository.delete(wishlistItem);

        wishlistRepository.save(wishlist);

        return wishlistMapper.fromEntityToResponse(wishlist);
    }

    @Override
    public List<WishlistResponse> getAllWishlists() {
        return wishlistRepository.findAll().stream()
                .map(wishlistMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

}
