package com.nexora.domain.services;

import java.time.LocalDateTime;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.CartItem;

public class CartService {
    public Cart createCart(Long id, Long userId) {
        return new Cart(id, userId, "ACTIVE", LocalDateTime.now());
    }

    public void addItem(Cart cart, Long itemId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        cart.getItems().add(new CartItem(itemId, cart.getId(), productId, quantity));
    }
}
