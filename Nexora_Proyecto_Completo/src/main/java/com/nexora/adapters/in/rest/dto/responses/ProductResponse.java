package com.nexora.adapters.in.rest.dto.responses;

import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price, ProductType type,
                             ProductStatus status, Long sellerId, Long categoryId) { }
