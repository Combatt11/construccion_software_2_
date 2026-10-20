package com.nexora.adapters.in.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.nexora.application.ports.in.ProductUseCase;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductStatus;
import com.nexora.enums.ProductType;
import com.nexora.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SellerController.class)
class SellerControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProductUseCase productUseCase;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserRepositoryPort userRepositoryPort;

    @Test
    void createProductSuccess() throws Exception {
        Product p = new Product(1L, "P", "D", new java.math.BigDecimal("10"), ProductType.PHYSICAL, ProductStatus.ACTIVE, 1L, 1L);
        when(productUseCase.createProduct(any(), any(), any(), any(), any(), any(), any())).thenReturn(p);
        String body = "{\"name\":\"P\",\"description\":\"D\",\"price\":10,\"type\":\"PHYSICAL\",\"categoryId\":1}";
        mockMvc.perform(post("/api/v1/seller/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}
