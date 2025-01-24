package com.alten.ecommerce.dtos.mappers;

import com.alten.ecommerce.dtos.responses.WishlistItemResponse;
import com.alten.ecommerce.dtos.responses.WishlistResponse;
import com.alten.ecommerce.entities.Wishlist;
import com.alten.ecommerce.entities.WishlistItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    @Mappings({
            @Mapping(source = "items", target = "wishlistItems"),
            @Mapping(source = "user.id", target = "userId")
    })
    WishlistResponse fromEntityToResponse(Wishlist wishlist);

    List<WishlistItemResponse> fromWishlistItemsToWishlistItemResponses(List<WishlistItem> wishlistItems);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(expression = "java(wishlistItem.getProduct().getPrice() * wishlistItem.getQuantity())", target = "totalItemPrice")
    WishlistItemResponse fromWishlistItemToResponse(WishlistItem wishlistItem);
}
