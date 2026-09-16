package com.nexora.domain;

import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;

public class User {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private UserStatus status;

    public User() { }

    public User(Long id, String name, String email, Role role, UserStatus status) {
        this.id = id; this.name = name; this.email = email; this.role = role; this.status = status;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}
