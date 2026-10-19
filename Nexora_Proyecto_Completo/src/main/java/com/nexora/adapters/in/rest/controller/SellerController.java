package com.nexora.adapters.in.rest.controller;

import com.nexora.adapters.in.rest.dto.requests.CreateProductRequest;
import com.nexora.adapters.in.rest.dto.responses.ProductResponse;
import com.nexora.adapters.in.rest.mapper.ProductRestMapper;
import com.nexora.application.ports.in.ProductUseCase;
import com.nexora.domain.models.Product;
import com.nexora.domain.models.User;
import com.nexora.enums.ProductType;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {
    private final ProductUseCase productUseCase;

    public SellerController(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@AuthenticationPrincipal User seller,
                                                        @RequestBody CreateProductRequest req) {
        Product product = productUseCase.createProduct(
                seller, System.currentTimeMillis(), req.name(), req.description(), req.price(),
                ProductType.valueOf(req.type()), req.categoryId());
        return ResponseEntity.ok(ProductRestMapper.toResponse(product));
    }
}
