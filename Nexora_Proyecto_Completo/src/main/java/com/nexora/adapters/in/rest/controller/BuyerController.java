package com.nexora.adapters.in.rest.controller;

import com.nexora.adapters.in.rest.dto.requests.CreateOrderRequest;
import com.nexora.adapters.in.rest.dto.requests.OrderItemRequest;
import com.nexora.adapters.in.rest.dto.responses.OrderResponse;
import com.nexora.adapters.in.rest.mapper.OrderRestMapper;
import com.nexora.application.ports.in.CartUseCase;
import com.nexora.application.ports.in.OrderUseCase;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/buyer")
@PreAuthorize("hasRole('BUYER')")
public class BuyerController {
    private final OrderUseCase orderUseCase;
    private final CartUseCase cartUseCase;

    public BuyerController(OrderUseCase orderUseCase, CartUseCase cartUseCase) {
        this.orderUseCase = orderUseCase;
        this.cartUseCase = cartUseCase;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@AuthenticationPrincipal User buyer,
                                                     @RequestBody CreateOrderRequest req) {
        Cart cart = cartUseCase.createCart(System.currentTimeMillis(), req.buyerId());
        for (OrderItemRequest item : req.items()) {
            cartUseCase.addItem(cart, System.currentTimeMillis(), item.productId(), item.quantity());
        }
        Order order = orderUseCase.createOrder(System.currentTimeMillis(), req.buyerId(), cart);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderUseCase.findById(id);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }

    @PostMapping("/orders/{id}/pay")
    public ResponseEntity<OrderResponse> pay(@PathVariable Long id) {
        Order order = orderUseCase.findById(id);
        orderUseCase.pay(order);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }

    @PostMapping("/orders/{id}/dispatch")
    public ResponseEntity<OrderResponse> dispatch(@PathVariable Long id) {
        Order order = orderUseCase.findById(id);
        orderUseCase.dispatch(order);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }

    @PostMapping("/orders/{id}/deliver")
    public ResponseEntity<OrderResponse> deliver(@PathVariable Long id) {
        Order order = orderUseCase.findById(id);
        orderUseCase.deliver(order);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }

    @PostMapping("/orders/{id}/finalize")
    public ResponseEntity<OrderResponse> finalizeOrder(@PathVariable Long id) {
        Order order = orderUseCase.findById(id);
        orderUseCase.finalizeOrder(order);
        return ResponseEntity.ok(OrderRestMapper.toResponse(order));
    }
}
