package com.nexora.adapters.in.rest.mapper;

import com.nexora.adapters.in.rest.dto.responses.ProductResponse;
import com.nexora.domain.models.Product;

public class ProductRestMapper {
    public static ProductResponse toResponse(Product p) {
        if (p == null) return null;
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getType(), p.getStatus(), p.getSellerId(), p.getCategoryId());
    }
}
