package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.nexora.application.ports.out.OrderRepositoryPort;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.CartItem;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.Product;
import com.nexora.enums.OrderStatus;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import java.util.Optional;

class OrderServiceTest {
    private OrderRepositoryPort orders;
    private ProductRepositoryPort products;
    private OrderService service;

    @BeforeEach
    void setup() {
        orders = Mockito.mock(OrderRepositoryPort.class);
        products = Mockito.mock(ProductRepositoryPort.class);
        service = new OrderService(orders, products);
    }

    @Test
    void createOrderSuccess() {
        Product p = new Product(10L, "P", "D", new BigDecimal("100"), ProductType.PHYSICAL, ProductStatus.ACTIVE, 1L, 1L);
        when(products.findById(10L)).thenReturn(Optional.of(p));
        Cart cart = new Cart(1L, 2L, "ACTIVE", java.time.LocalDateTime.now());
        cart.getItems().add(new CartItem(1L, 1L, 10L, 2));
        Order o = service.createOrder(1L, 2L, cart);
        assertEquals(OrderStatus.PENDING_PAYMENT, o.getStatus());
        assertEquals(new BigDecimal("200"), o.getTotal());
        verify(orders).save(o);
    }

    @Test
    void createOrderEmptyCartThrows() {
        Cart cart = new Cart(1L, 2L, "ACTIVE", java.time.LocalDateTime.now());
        assertThrows(IllegalArgumentException.class, () -> service.createOrder(1L, 2L, cart));
    }

    @Test
    void createOrderMissingProductThrows() {
        when(products.findById(10L)).thenReturn(Optional.empty());
        Cart cart = new Cart(1L, 2L, "ACTIVE", java.time.LocalDateTime.now());
        cart.getItems().add(new CartItem(1L, 1L, 10L, 2));
        assertThrows(IllegalArgumentException.class, () -> service.createOrder(1L, 2L, cart));
    }

    @Test
    void payTransition() {
        Order o = new Order(1L, 2L, OrderStatus.PENDING_PAYMENT, java.time.LocalDateTime.now(), BigDecimal.TEN);
        service.pay(o);
        assertEquals(OrderStatus.PAID, o.getStatus());
    }

    @Test
    void payInvalidStateThrows() {
        Order o = new Order(1L, 2L, OrderStatus.PAID, java.time.LocalDateTime.now(), BigDecimal.TEN);
        assertThrows(IllegalStateException.class, () -> service.pay(o));
    }

    @Test
    void fullCycle() {
        Order o = new Order(1L, 2L, OrderStatus.PENDING_PAYMENT, java.time.LocalDateTime.now(), BigDecimal.TEN);
        service.pay(o); service.dispatch(o); service.deliver(o); service.finalizeOrder(o);
        assertEquals(OrderStatus.FINALIZED, o.getStatus());
    }

    @Test
    void finalizeInvalidThrows() {
        Order o = new Order(1L, 2L, OrderStatus.PAID, java.time.LocalDateTime.now(), BigDecimal.TEN);
        assertThrows(IllegalStateException.class, () -> service.finalizeOrder(o));
    }

    @Test
    void findById() {
        Order o = new Order(1L, 2L, OrderStatus.PAID, java.time.LocalDateTime.now(), BigDecimal.TEN);
        when(orders.findById(1L)).thenReturn(Optional.of(o));
        assertEquals(o, service.findById(1L));
    }

    @Test
    void findByIdNotFoundThrows() {
        when(orders.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.findById(99L));
    }
}
