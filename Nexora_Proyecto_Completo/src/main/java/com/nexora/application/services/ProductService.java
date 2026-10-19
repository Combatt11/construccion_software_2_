package com.nexora.application.services;

import com.nexora.application.ports.in.ProductUseCase;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;
import java.math.BigDecimal;

public class ProductService implements ProductUseCase {
    private final ProductRepositoryPort products;

    public ProductService(ProductRepositoryPort products) {
        this.products = products;
    }

    @Override
    public Product createProduct(User user, Long id, String name, String description, BigDecimal price,
                                 ProductType type, Long categoryId) {
        requireRole(user, Role.SELLER, Role.ADMINISTRATOR);
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (products.findById(id).isPresent()) throw new IllegalArgumentException("El producto ya existe.");
        Long sellerId = user.getRole() == Role.SELLER ? user.getId() : null;
        Product product = new Product(id, name, description, price, type, ProductStatus.ACTIVE, sellerId, categoryId);
        products.save(product);
        return product;
    }

    private void requireRole(User user, Role... roles) {
        if (user == null) throw new IllegalArgumentException("El usuario debe estar autenticado.");
        for (Role role : roles) if (user.getRole() == role) return;
        throw new IllegalArgumentException("El usuario no tiene permisos para esta operación.");
    }
}
