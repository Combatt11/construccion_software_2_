package com.nexora.application.ports.in;

import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductType;
import java.math.BigDecimal;

public interface ProductUseCase {
    Product createProduct(User user, Long id, String name, String description, BigDecimal price,
                          ProductType type, Long categoryId);
}
