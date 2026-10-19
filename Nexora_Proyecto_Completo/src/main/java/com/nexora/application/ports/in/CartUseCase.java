package com.nexora.application.ports.in;

import com.nexora.domain.models.Cart;

public interface CartUseCase {
    Cart createCart(Long id, Long userId);
    void addItem(Cart cart, Long itemId, Long productId, int quantity);
}
