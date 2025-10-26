package com.company.recruitmentsystem.service;

import com.company.recruitmentsystem.dto.auth.AuthResponse;
import com.company.recruitmentsystem.dto.auth.LoginRequest;
import com.company.recruitmentsystem.dto.auth.SignupRequest;

public interface AuthService {
	AuthResponse signup(SignupRequest request);
	AuthResponse login(LoginRequest request);
}
