package com.contactmanagementsystem.cms.services;

import com.contactmanagementsystem.cms.dto.JwtResponse;
import com.contactmanagementsystem.cms.dto.LoginRequest;
import com.contactmanagementsystem.cms.dto.MessageResponse;
import com.contactmanagementsystem.cms.dto.SignupRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(SignupRequest signUpRequest);
    MessageResponse registerAdmin(SignupRequest signUpRequest);  // New method for admin registration

}