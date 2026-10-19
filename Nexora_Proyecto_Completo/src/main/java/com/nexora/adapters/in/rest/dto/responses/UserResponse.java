package com.nexora.adapters.in.rest.dto.responses;

import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;

public record UserResponse(Long id, String name, String email, Role role, UserStatus status) { }
