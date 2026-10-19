package com.nexora.adapters.in.rest.mapper;

import com.nexora.adapters.in.rest.dto.responses.OrderItemResponse;
import com.nexora.adapters.in.rest.dto.responses.OrderResponse;
import com.nexora.domain.models.Order;
import com.nexora.domain.models.OrderItem;
import java.util.List;

public class OrderRestMapper {
    public static OrderResponse toResponse(Order o) {
        if (o == null) return null;
        List<OrderItemResponse> items = o.getItems().stream()
                .map(i -> new OrderItemResponse(i.getId(), i.getProductId(), i.getQuantity(), i.getUnitPrice(), i.getSubtotal()))
                .toList();
        return new OrderResponse(o.getId(), o.getBuyerId(), o.getStatus(), o.getCreationDate(), o.getTotal(), items);
    }
}
