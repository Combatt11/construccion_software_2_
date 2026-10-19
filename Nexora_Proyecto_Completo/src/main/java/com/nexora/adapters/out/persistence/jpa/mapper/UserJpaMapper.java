package com.nexora.adapters.out.persistence.jpa.mapper;

import com.nexora.adapters.out.persistence.jpa.entity.UserJpaEntity;
import com.nexora.domain.models.User;
import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;

public class UserJpaMapper {
    public static User toDomain(UserJpaEntity e) {
        if (e == null) return null;
        User u = new User();
        u.setId(e.getId());
        u.setName(e.getName());
        u.setEmail(e.getEmail());
        u.setRole(Role.valueOf(e.getRole()));
        u.setStatus(UserStatus.valueOf(e.getStatus()));
        u.setPassword(e.getPassword());
        u.setAuthTokenVersion(e.getAuthTokenVersion());
        return u;
    }

    public static UserJpaEntity toEntity(User u) {
        if (u == null) return null;
        UserJpaEntity e = new UserJpaEntity();
        e.setId(u.getId());
        e.setName(u.getName());
        e.setEmail(u.getEmail());
        e.setRole(u.getRole() == null ? null : u.getRole().name());
        e.setStatus(u.getStatus() == null ? null : u.getStatus().name());
        e.setPassword(u.getPassword());
        e.setAuthTokenVersion(u.getAuthTokenVersion());
        return e;
    }
}
