package com.example.user.services;

import com.example.user.dto.SignUpRequest;

public interface AuthService {
    boolean createCustomer(SignUpRequest signUpRequest);
}
