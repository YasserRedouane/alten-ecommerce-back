package com.alten.ecommerce.dtos.responses;

import com.alten.ecommerce.entities.User;
import com.alten.ecommerce.entities.WishlistItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private Long userId;
    private List<WishlistItemResponse> wishlistItems;
}
