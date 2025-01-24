package com.alten.ecommerce.dtos.mappers;

import com.alten.ecommerce.dtos.requests.ProductRequest;
import com.alten.ecommerce.dtos.responses.ProductResponse;
import com.alten.ecommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product fromRequestToEntity(ProductRequest productRequest);
    ProductResponse fromEntityToResponse(Product product);
}
