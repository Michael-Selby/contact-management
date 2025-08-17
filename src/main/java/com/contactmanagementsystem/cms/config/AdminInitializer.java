package com.contactmanagementsystem.cms.config;

import com.contactmanagementsystem.cms.entity.ERole;
import com.contactmanagementsystem.cms.entity.Role;
import com.contactmanagementsystem.cms.entity.User;
import com.contactmanagementsystem.cms.repository.RoleRepository;
import com.contactmanagementsystem.cms.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.email:admin@example.com}")
    private String adminEmail;

    @Value("${app.admin.password:admin123}")
    private String adminPassword;

    @Value("${app.admin.enabled:true}")
    private boolean adminEnabled;

    @PostConstruct
    public void init() {
        if (!adminEnabled) {
            return;
        }

        // Create roles if they don't exist
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_ADMIN)));

        roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));

        // Create initial admin user if it doesn't exist
        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            userRepository.save(admin);

            System.out.println("===========================================");
            System.out.println("Initial admin user created!");
            System.out.println("Username: " + adminUsername);
            System.out.println("Password: " + adminPassword);
            System.out.println("IMPORTANT: Change this password after first login!");
            System.out.println("===========================================");
        }
    }
}