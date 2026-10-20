package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductServiceTest {
    private ProductRepositoryPort products;
    private ProductService service;

    @BeforeEach
    void setup() {
        products = Mockito.mock(ProductRepositoryPort.class);
        service = new ProductService(products);
    }

    @Test
    void createProductBySeller() {
        User seller = new User(1L, "Seller", "s@x.com", Role.SELLER, UserStatus.ACTIVE);
        when(products.findById(10L)).thenReturn(java.util.Optional.empty());
        Product p = service.createProduct(seller, 10L, "Prod", "Desc", new java.math.BigDecimal("10"), ProductType.PHYSICAL, 1L);
        assertEquals(1L, p.getSellerId());
        assertEquals(ProductStatus.ACTIVE, p.getStatus());
        verify(products).save(p);
    }

    @Test
    void createProductByAdminHasNoSeller() {
        User admin = new User(1L, "Admin", "a@x.com", Role.ADMINISTRATOR, UserStatus.ACTIVE);
        when(products.findById(10L)).thenReturn(java.util.Optional.empty());
        Product p = service.createProduct(admin, 10L, "Prod", "Desc", new java.math.BigDecimal("10"), ProductType.PHYSICAL, 1L);
        assertNull(p.getSellerId());
    }

    @Test
    void createProductDuplicateThrows() {
        User seller = new User(1L, "Seller", "s@x.com", Role.SELLER, UserStatus.ACTIVE);
        when(products.findById(10L)).thenReturn(java.util.Optional.of(new Product()));
        assertThrows(IllegalArgumentException.class, () -> service.createProduct(seller, 10L, "Prod", "Desc", new java.math.BigDecimal("10"), ProductType.PHYSICAL, 1L));
    }

    @Test
    void createProductNegativePriceThrows() {
        User seller = new User(1L, "Seller", "s@x.com", Role.SELLER, UserStatus.ACTIVE);
        assertThrows(IllegalArgumentException.class, () -> service.createProduct(seller, 10L, "Prod", "Desc", new java.math.BigDecimal("-1"), ProductType.PHYSICAL, 1L));
    }

    @Test
    void createProductUnauthorizedThrows() {
        User buyer = new User(1L, "Buyer", "b@x.com", Role.BUYER, UserStatus.ACTIVE);
        assertThrows(IllegalArgumentException.class, () -> service.createProduct(buyer, 10L, "Prod", "Desc", new java.math.BigDecimal("10"), ProductType.PHYSICAL, 1L));
    }
}
