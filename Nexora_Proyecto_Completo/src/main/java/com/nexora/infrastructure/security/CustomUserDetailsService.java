package com.nexora.infrastructure.security;

import com.nexora.application.ports.out.UserRepositoryPort;
import com.nexora.domain.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositoryPort users;

    public CustomUserDetailsService(UserRepositoryPort users) { this.users = users; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        String password = user.getPassword() == null ? "" : user.getPassword();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(password)
                .authorities(new SimpleGrantedAuthority("ROLE_" + (user.getRole() == null ? "USER" : user.getRole().name())))
                .build();
    }
}
