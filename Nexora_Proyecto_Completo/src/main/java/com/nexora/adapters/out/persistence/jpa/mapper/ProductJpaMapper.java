package com.nexora.adapters.out.persistence.jpa.mapper;

import com.nexora.adapters.out.persistence.jpa.entity.ProductJpaEntity;
import com.nexora.domain.models.Product;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;

public class ProductJpaMapper {
    public static Product toDomain(ProductJpaEntity e) {
        if (e == null) return null;
        Product p = new Product();
        p.setId(e.getId());
        p.setName(e.getName());
        p.setDescription(e.getDescription());
        p.setPrice(e.getPrice());
        p.setType(e.getType() == null ? null : ProductType.valueOf(e.getType()));
        p.setStatus(e.getStatus() == null ? null : ProductStatus.valueOf(e.getStatus()));
        p.setSellerId(e.getSellerId());
        p.setCategoryId(e.getCategoryId());
        return p;
    }

    public static ProductJpaEntity toEntity(Product p) {
        if (p == null) return null;
        ProductJpaEntity e = new ProductJpaEntity();
        e.setId(p.getId());
        e.setName(p.getName());
        e.setDescription(p.getDescription());
        e.setPrice(p.getPrice());
        e.setType(p.getType() == null ? null : p.getType().name());
        e.setStatus(p.getStatus() == null ? null : p.getStatus().name());
        e.setSellerId(p.getSellerId());
        e.setCategoryId(p.getCategoryId());
        return e;
    }
}
