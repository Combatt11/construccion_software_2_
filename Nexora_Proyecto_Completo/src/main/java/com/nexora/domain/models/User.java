package com.nexora.domain.models;

import com.nexora.enums.Role;
import com.nexora.enums.UserStatus;

public class User {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private UserStatus status;
    private String password;
    private Integer authTokenVersion = 0;

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
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Integer getAuthTokenVersion() { return authTokenVersion; }
    public void setAuthTokenVersion(Integer authTokenVersion) { this.authTokenVersion = authTokenVersion == null ? 0 : authTokenVersion; }

    public void bumpAuthTokenVersion() {
        this.authTokenVersion = (this.authTokenVersion == null ? 0 : this.authTokenVersion + 1);
    }
}
