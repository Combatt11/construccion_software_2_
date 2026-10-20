package com.nexora.adapters.in.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.nexora.application.ports.in.UserUseCase;
import com.nexora.application.ports.out.ProductRepositoryPort;
import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;
import com.nexora.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AdminController.class)
class AdminControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserUseCase userUseCase;
    @MockitoBean
    private PasswordEncoder passwordEncoder;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserRepositoryPort userRepositoryPort;
    @MockitoBean
    private ProductRepositoryPort productRepositoryPort;

    @Test
    void registerUserSuccess() throws Exception {
        User u = new User(1L, "U", "u@x.com", Role.BUYER, UserStatus.ACTIVE);
        when(passwordEncoder.encode("pass")).thenReturn("hash");
        when(userUseCase.register(any(), any(), any(), any(), any())).thenReturn(u);
        String body = "{\"name\":\"U\",\"email\":\"u@x.com\",\"password\":\"pass\",\"role\":\"BUYER\"}";
        mockMvc.perform(post("/api/v1/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }
}
