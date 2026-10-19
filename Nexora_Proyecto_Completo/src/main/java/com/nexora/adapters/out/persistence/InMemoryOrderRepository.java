package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.OrderRepositoryPort;
import com.nexora.domain.models.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepositoryPort {
    private final List<Order> data = new ArrayList<>();

    @Override
    public Order save(Order order) { data.add(order); return order; }

    @Override
    public Optional<Order> findById(Long id) {
        return data.stream().filter(o -> id != null && id.equals(o.getId())).findFirst();
    }

    @Override
    public List<Order> findAll() { return new ArrayList<>(data); }
}
