package com.nexora.adapters.out.persistence.jpa.adapter;

import com.nexora.adapters.out.persistence.jpa.entity.ProductJpaEntity;
import com.nexora.adapters.out.persistence.jpa.mapper.ProductJpaMapper;
import com.nexora.adapters.out.persistence.jpa.repository.SpringDataJpaProductRepository;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ProductJpaAdapter implements ProductRepositoryPort {
    private final SpringDataJpaProductRepository repo;

    public ProductJpaAdapter(SpringDataJpaProductRepository repo) { this.repo = repo; }

    @Override
    public Product save(Product product) { return ProductJpaMapper.toDomain(repo.save(ProductJpaMapper.toEntity(product))); }

    @Override
    public Optional<Product> findById(Long id) { return repo.findById(id).map(ProductJpaMapper::toDomain); }

    @Override
    public List<Product> findAll() { return repo.findAll().stream().map(ProductJpaMapper::toDomain).toList(); }
}
