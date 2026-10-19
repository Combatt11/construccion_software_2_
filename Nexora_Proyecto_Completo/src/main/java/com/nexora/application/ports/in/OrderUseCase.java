package com.nexora.application.ports.in;

import com.nexora.domain.models.Cart;
import com.nexora.domain.models.Order;

public interface OrderUseCase {
    Order createOrder(Long id, Long buyerId, Cart cart);
    void pay(Order order);
    void dispatch(Order order);
    void deliver(Order order);
    void finalizeOrder(Order order);
}
