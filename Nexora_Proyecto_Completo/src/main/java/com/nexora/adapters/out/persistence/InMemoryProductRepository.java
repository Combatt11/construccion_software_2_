package com.nexora.adapters.out.persistence;

import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.domain.models.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepositoryPort {
    private final List<Product> data = new ArrayList<>();

    @Override
    public Product save(Product product) { data.add(product); return product; }

    @Override
    public Optional<Product> findById(Long id) {
        return data.stream().filter(p -> id != null && id.equals(p.getId())).findFirst();
    }

    @Override
    public List<Product> findAll() { return new ArrayList<>(data); }
}
