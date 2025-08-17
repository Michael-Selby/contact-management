package com.contactmanagementsystem.cms.services;

import com.contactmanagementsystem.cms.dto.JwtResponse;
import com.contactmanagementsystem.cms.dto.LoginRequest;
import com.contactmanagementsystem.cms.dto.MessageResponse;
import com.contactmanagementsystem.cms.dto.SignupRequest;
import com.contactmanagementsystem.cms.entity.ERole;
import com.contactmanagementsystem.cms.entity.Role;
import com.contactmanagementsystem.cms.entity.User;
import com.contactmanagementsystem.cms.mapper.UserMapper;
import com.contactmanagementsystem.cms.repository.RoleRepository;
import com.contactmanagementsystem.cms.repository.UserRepository;

import com.contactmanagementsystem.cms.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserMapper userMapper;

    // In AuthServiceImpl.java, update the authenticateUser method:

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the UserDetails from the authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Pass the UserDetails to generateJwtToken
        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JwtResponse.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }


    // In AuthServiceImpl.java
    @Override
    @Transactional
    public MessageResponse registerAdmin(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new admin account
        User admin = userMapper.signupRequestToUser(signUpRequest);
        admin.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Assign both ADMIN and USER roles
        Set<Role> roles = new HashSet<>();

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseGet(() -> {
                    Role newAdminRole = new Role(ERole.ROLE_ADMIN);
                    return roleRepository.save(newAdminRole);
                });
        roles.add(adminRole);

        // Also add USER role as a fallback
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> {
                    Role newUserRole = new Role(ERole.ROLE_USER);
                    return roleRepository.save(newUserRole);
                });
        roles.add(userRole);

        admin.setRoles(roles);
        userRepository.save(admin);

        return new MessageResponse("Admin registered successfully!");
    }
    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = userMapper.signupRequestToUser(signUpRequest);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        // Assign default ROLE_USER to all new users
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role USER is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }
}