package com.alten.ecommerce.repositories;

import com.alten.ecommerce.entities.Cart;
import com.alten.ecommerce.entities.CartItem;
import com.alten.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
