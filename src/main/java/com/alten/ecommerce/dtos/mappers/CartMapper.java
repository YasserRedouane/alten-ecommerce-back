package com.alten.ecommerce.dtos.mappers;


import com.alten.ecommerce.dtos.responses.CartItemResponse;
import com.alten.ecommerce.dtos.responses.CartResponse;
import com.alten.ecommerce.entities.Cart;
import com.alten.ecommerce.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mappings({
            @Mapping(source = "items", target = "cartItems"),
            @Mapping(source = "user.id", target = "userId")
    })
    CartResponse fromEntityToResponse(Cart cart);

    List<CartItemResponse> fromCartItemsToCartItemResponses(List<CartItem> cartItems);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(expression = "java(cartItem.getProduct().getPrice() * cartItem.getQuantity())", target = "totalItemPrice")
    CartItemResponse fromCartItemToResponse(CartItem cartItem);
}
