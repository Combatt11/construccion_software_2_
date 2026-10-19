package com.nexora.application.services;

import com.nexora.application.ports.in.OrderUseCase;
import com.nexora.application.ports.out.OrderRepositoryPort;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.CartItem;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.OrderItem;
import com.nexora.domain.models.Product;
import com.nexora.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderService implements OrderUseCase {
    private final OrderRepositoryPort orders;
    private final ProductRepositoryPort products;

    public OrderService(OrderRepositoryPort orders, ProductRepositoryPort products) {
        this.orders = orders;
        this.products = products;
    }

    @Override
    public Order createOrder(Long id, Long buyerId, Cart cart) {
        if (cart.getItems().isEmpty()) throw new IllegalArgumentException("El carrito está vacío.");
        Order order = new Order(id, buyerId, OrderStatus.PENDING_PAYMENT, LocalDateTime.now(), BigDecimal.ZERO);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            Product product = products.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado."));
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            order.getItems().add(new OrderItem((long) order.getItems().size() + 1, id, product.getId(),
                    cartItem.getQuantity(), product.getPrice(), subtotal));
            total = total.add(subtotal);
        }
        order.setTotal(total);
        orders.save(order);
        return order;
    }

    @Override
    public void pay(Order order) {
        requireStatus(order, OrderStatus.PENDING_PAYMENT);
        order.setStatus(OrderStatus.PAID);
    }

    @Override
    public void dispatch(Order order) {
        requireStatus(order, OrderStatus.PAID);
        order.setStatus(OrderStatus.DISPATCHED);
    }

    @Override
    public void deliver(Order order) {
        requireStatus(order, OrderStatus.DISPATCHED);
        order.setStatus(OrderStatus.DELIVERED);
    }

    @Override
    public void finalizeOrder(Order order) {
        requireStatus(order, OrderStatus.DELIVERED);
        order.setStatus(OrderStatus.FINALIZED);
    }

    private void requireStatus(Order order, OrderStatus expected) {
        if (order.getStatus() != expected) throw new IllegalStateException("Estado no permitido: " + order.getStatus());
    }
}
