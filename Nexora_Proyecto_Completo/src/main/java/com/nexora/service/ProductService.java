package com.nexora.service;

import java.math.BigDecimal;
import com.nexora.domain.Product;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;
import com.nexora.domain.User;
import com.nexora.repository.InMemoryRepository;

public class ProductService {
    private final InMemoryRepository<Product> products = new InMemoryRepository<>();

    public Product createProduct(User user, Long id, String name, String description, BigDecimal price,
                                 ProductType type, Long categoryId) {
        requireRole(user, Role.SELLER, Role.ADMINISTRATOR);
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El precio no puede ser negativo.");
        if (products.findFirst(p -> id.equals(p.getId())).isPresent()) throw new IllegalArgumentException("El producto ya existe.");
        Long sellerId = user.getRole() == Role.SELLER ? user.getId() : null;
        Product product = new Product(id, name, description, price, type, ProductStatus.ACTIVE, sellerId, categoryId);
        products.save(product);
        return product;
    }

    public InMemoryRepository<Product> getRepository() { return products; }

    private void requireRole(User user, Role... roles) {
        if (user == null) throw new IllegalArgumentException("El usuario debe estar autenticado.");
        for (Role role : roles) if (user.getRole() == role) return;
        throw new IllegalArgumentException("El usuario no tiene permisos para esta operación.");
    }
}
