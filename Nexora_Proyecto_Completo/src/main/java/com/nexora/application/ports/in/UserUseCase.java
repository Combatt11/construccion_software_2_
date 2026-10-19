package com.nexora.application.ports.in;

import com.nexora.domain.models.User;
import com.nexora.enums.Role;

public interface UserUseCase {
    User registerBuyer(Long id, String name, String email);
    User register(Long id, String name, String email, Role role);
    User onboardSeller(User administrator, Long id, String name, String email);
}
