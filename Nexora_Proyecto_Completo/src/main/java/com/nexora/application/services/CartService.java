package com.nexora.application.services;

import com.nexora.application.ports.in.CartUseCase;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.CartItem;
import java.time.LocalDateTime;

public class CartService implements CartUseCase {
    @Override
    public Cart createCart(Long id, Long userId) {
        return new Cart(id, userId, "ACTIVE", LocalDateTime.now());
    }

    @Override
    public void addItem(Cart cart, Long itemId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        cart.getItems().add(new CartItem(itemId, cart.getId(), productId, quantity));
    }
}
