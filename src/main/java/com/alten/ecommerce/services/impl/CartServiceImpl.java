package com.alten.ecommerce.services.impl;

import com.alten.ecommerce.dtos.mappers.CartMapper;
import com.alten.ecommerce.dtos.requests.CartRequest;
import com.alten.ecommerce.dtos.responses.CartResponse;
import com.alten.ecommerce.entities.Cart;
import com.alten.ecommerce.entities.CartItem;
import com.alten.ecommerce.entities.Product;
import com.alten.ecommerce.entities.User;
import com.alten.ecommerce.exceptions.ResourceNotFoundException;
import com.alten.ecommerce.repositories.CartItemRepository;
import com.alten.ecommerce.repositories.CartRepository;
import com.alten.ecommerce.repositories.ProductRepository;
import com.alten.ecommerce.repositories.UserRepository;
import com.alten.ecommerce.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartResponse getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));

        return cartMapper.fromEntityToResponse(cart);
    }

    @Override
    public CartResponse addProductToCart(Long userId, CartRequest cartItemRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + cartItemRequest.getProductId()));

        Cart newCart = cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(Cart.builder().user(user).build()));

        CartItem cartItem = CartItem.builder()
                .cart(newCart).product(product).quantity(cartItemRequest.getQuantity()).build();
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        newCart.getItems().add(savedCartItem);

        cartRepository.save(newCart);

        return cartMapper.fromEntityToResponse(newCart);
    }

    @Override
    public CartResponse removeProductFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        cartRepository.save(cart);

        return cartMapper.fromEntityToResponse(cart);
    }

    @Override
    public List<CartResponse> getAllCarts() {
        return cartRepository.findAll().stream()
                .map(cartMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse updateProductQuantity(Long userId, Long productId, Integer quantity) {
        // Fetch the cart for the given user ID
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));

        // Find the product by productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        // Find the cart item containing the product
        Optional<CartItem> cartItemOpt = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst();

        if (cartItemOpt.isPresent()) {
            // Product is in the cart, update its quantity
            CartItem cartItem = cartItemOpt.get();
            cartItem.setQuantity(quantity);
            cartRepository.save(cart);
        } else {
            // Product is not in the cart, add it with the specified quantity
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cart.getItems().add(newCartItem);
            cartRepository.save(cart);
        }

        // Return the updated cart response
        return cartMapper.fromEntityToResponse(cart);
    }
}
