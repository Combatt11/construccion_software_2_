package com.nexora.adapters.out.persistence.jpa.mapper;

import com.nexora.adapters.out.persistence.jpa.entity.OrderItemJpaEntity;
import com.nexora.adapters.out.persistence.jpa.entity.OrderJpaEntity;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.OrderItem;
import com.nexora.enums.OrderStatus;
import java.util.ArrayList;
import java.util.List;

public class OrderJpaMapper {
    public static Order toDomain(OrderJpaEntity e) {
        if (e == null) return null;
        Order o = new Order();
        o.setId(e.getId());
        o.setBuyerId(e.getBuyerId());
        o.setStatus(e.getStatus() == null ? null : OrderStatus.valueOf(e.getStatus()));
        o.setCreationDate(e.getCreationDate());
        o.setTotal(e.getTotal());
        List<OrderItem> items = new ArrayList<>();
        if (e.getItems() != null) {
            for (OrderItemJpaEntity ie : e.getItems()) {
                OrderItem i = new OrderItem();
                i.setId(ie.getId());
                i.setOrderId(e.getId());
                i.setProductId(ie.getProductId());
                i.setQuantity(ie.getQuantity());
                i.setUnitPrice(ie.getUnitPrice());
                i.setSubtotal(ie.getSubtotal());
                items.add(i);
            }
        }
        for (OrderItem i : items) o.getItems().add(i);
        return o;
    }

    public static OrderJpaEntity toEntity(Order o) {
        if (o == null) return null;
        OrderJpaEntity e = new OrderJpaEntity();
        e.setId(o.getId());
        e.setBuyerId(o.getBuyerId());
        e.setStatus(o.getStatus() == null ? null : o.getStatus().name());
        e.setCreationDate(o.getCreationDate());
        e.setTotal(o.getTotal());
        List<OrderItemJpaEntity> items = new ArrayList<>();
        for (OrderItem i : o.getItems()) {
            OrderItemJpaEntity ie = new OrderItemJpaEntity();
            ie.setId(i.getId());
            ie.setOrder(e);
            ie.setProductId(i.getProductId());
            ie.setQuantity(i.getQuantity());
            ie.setUnitPrice(i.getUnitPrice());
            ie.setSubtotal(i.getSubtotal());
            items.add(ie);
        }
        e.setItems(items);
        return e;
    }
}
