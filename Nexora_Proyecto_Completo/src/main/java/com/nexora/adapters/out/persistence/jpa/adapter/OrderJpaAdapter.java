package com.nexora.adapters.out.persistence.jpa.adapter;

import com.nexora.adapters.out.persistence.jpa.entity.OrderJpaEntity;
import com.nexora.adapters.out.persistence.jpa.mapper.OrderJpaMapper;
import com.nexora.adapters.out.persistence.jpa.repository.SpringDataJpaOrderRepository;
import com.nexora.application.ports.out.OrderRepositoryPort;
import com.nexora.domain.models.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class OrderJpaAdapter implements OrderRepositoryPort {
    private final SpringDataJpaOrderRepository repo;

    public OrderJpaAdapter(SpringDataJpaOrderRepository repo) { this.repo = repo; }

    @Override
    public Order save(Order order) { return OrderJpaMapper.toDomain(repo.save(OrderJpaMapper.toEntity(order))); }

    @Override
    public Optional<Order> findById(Long id) { return repo.findById(id).map(OrderJpaMapper::toDomain); }

    @Override
    public List<Order> findAll() { return repo.findAll().stream().map(OrderJpaMapper::toDomain).toList(); }
}
