package com.nexora.adapters.in.rest.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderRequest(
        @NotNull Long buyerId,
        @NotEmpty List<OrderItemRequest> items
) { }
