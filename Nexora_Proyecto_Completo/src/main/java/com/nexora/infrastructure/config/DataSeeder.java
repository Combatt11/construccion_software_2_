package com.nexora.infrastructure.config;

import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserRepositoryPort users;
    private final ProductRepositoryPort products;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepositoryPort users, ProductRepositoryPort products, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.products = products;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (users.findByEmail("admin@nexora.com").isEmpty()) {
            User admin = new User(1L, "Admin", "admin@nexora.com", Role.ADMINISTRATOR, UserStatus.ACTIVE);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setAuthTokenVersion(0);
            users.save(admin);
        }
        if (users.findByEmail("seller@nexora.com").isEmpty()) {
            User seller = new User(2L, "Seller", "seller@nexora.com", Role.SELLER, UserStatus.ACTIVE);
            seller.setPassword(passwordEncoder.encode("seller123"));
            seller.setAuthTokenVersion(0);
            users.save(seller);
        }
        if (products.findAll().isEmpty()) {
            products.save(new Product(10L, "Audífonos Nexora", "Producto demo", new java.math.BigDecimal("120000"),
                    ProductType.PHYSICAL, ProductStatus.ACTIVE, 2L, 1L));
            products.save(new Product(11L, "Ebook Nexora", "Producto digital", new java.math.BigDecimal("50000"),
                    ProductType.DIGITAL, ProductStatus.ACTIVE, 2L, 1L));
        }
    }
}
