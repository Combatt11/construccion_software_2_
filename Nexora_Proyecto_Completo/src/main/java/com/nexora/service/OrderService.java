package com.nexora.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.nexora.domain.Cart;
import com.nexora.domain.CartItem;
import com.nexora.domain.Order;
import com.nexora.domain.OrderItem;
import com.nexora.domain.Product;
import com.nexora.enums.OrderStatus;
import com.nexora.repository.InMemoryRepository;

public class OrderService {
    private final InMemoryRepository<Order> orders = new InMemoryRepository<>();

    public Order createOrder(Long id, Long buyerId, Cart cart, InMemoryRepository<Product> products) {
        if (cart.getItems().isEmpty()) throw new IllegalArgumentException("El carrito está vacío.");
        Order order = new Order(id, buyerId, OrderStatus.PENDING_PAYMENT, LocalDateTime.now(), BigDecimal.ZERO);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            Product product = products.findFirst(p -> cartItem.getProductId().equals(p.getId()))
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

    public void pay(Order order) {
        requireStatus(order, OrderStatus.PENDING_PAYMENT);
        order.setStatus(OrderStatus.PAID);
    }

    public void dispatch(Order order) {
        requireStatus(order, OrderStatus.PAID);
        order.setStatus(OrderStatus.DISPATCHED);
    }

    public void deliver(Order order) {
        requireStatus(order, OrderStatus.DISPATCHED);
        order.setStatus(OrderStatus.DELIVERED);
    }

    public void finalizeOrder(Order order) {
        requireStatus(order, OrderStatus.DELIVERED);
        order.setStatus(OrderStatus.FINALIZED);
    }

    public InMemoryRepository<Order> getRepository() { return orders; }

    private void requireStatus(Order order, OrderStatus expected) {
        if (order.getStatus() != expected) throw new IllegalStateException("Estado no permitido: " + order.getStatus());
    }
}
