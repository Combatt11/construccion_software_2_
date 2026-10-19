package com.nexora.adapters.in.rest.mapper;

import com.nexora.adapters.in.rest.dto.responses.UserResponse;
import com.nexora.domain.models.User;

public class UserRestMapper {
    public static UserResponse toResponse(User u) {
        if (u == null) return null;
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole(), u.getStatus());
    }
}
