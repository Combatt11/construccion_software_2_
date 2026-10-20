package com.nexora.application.services;

import static org.junit.jupiter.api.Assertions.*;
import com.nexora.domain.models.Cart;
import org.junit.jupiter.api.Test;

class CartServiceTest {
    private final CartService service = new CartService();

    @Test
    void createCart() {
        Cart c = service.createCart(1L, 2L);
        assertEquals("ACTIVE", c.getStatus());
        assertTrue(c.getItems().isEmpty());
    }

    @Test
    void addItem() {
        Cart c = service.createCart(1L, 2L);
        service.addItem(c, 1L, 10L, 2);
        assertEquals(1, c.getItems().size());
        assertEquals(2, c.getItems().get(0).getQuantity());
    }

    @Test
    void addItemZeroThrows() {
        Cart c = service.createCart(1L, 2L);
        assertThrows(IllegalArgumentException.class, () -> service.addItem(c, 1L, 10L, 0));
    }
}
