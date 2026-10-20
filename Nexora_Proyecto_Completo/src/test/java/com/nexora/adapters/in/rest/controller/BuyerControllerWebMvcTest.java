package com.nexora.adapters.in.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.nexora.application.ports.in.CartUseCase;
import com.nexora.application.ports.in.OrderUseCase;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.Cart;
import com.nexora.domain.models.Order;
import com.nexora.enums.OrderStatus;
import com.nexora.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BuyerController.class)
class BuyerControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderUseCase orderUseCase;
    @MockitoBean
    private CartUseCase cartUseCase;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserRepositoryPort userRepositoryPort;

    @Test
    void createOrderSuccess() throws Exception {
        Cart cart = new Cart(1L, 2L, "ACTIVE", java.time.LocalDateTime.now());
        when(cartUseCase.createCart(any(), any())).thenReturn(cart);
        Order order = new Order(1L, 2L, OrderStatus.PENDING_PAYMENT, java.time.LocalDateTime.now(), new java.math.BigDecimal("100"));
        when(orderUseCase.createOrder(any(), any(), any())).thenReturn(order);
        String body = "{\"buyerId\":2,\"items\":[{\"productId\":10,\"quantity\":1}]}";
        mockMvc.perform(post("/api/v1/buyer/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void getOrderNotFound() throws Exception {
        when(orderUseCase.findById(99L)).thenThrow(new IllegalArgumentException("Pedido no encontrado."));
        mockMvc.perform(get("/api/v1/buyer/orders/99"))
                .andExpect(status().isInternalServerError());
    }
}
