package com.alten.ecommerce.repositories;

import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.entities.Wishlist;
import com.alten.ecommerce.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    Optional<WishlistItem> findByWishlistAndProduct(Wishlist wishlist, Product product);
}