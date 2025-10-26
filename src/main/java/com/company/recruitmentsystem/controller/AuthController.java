package com.company.recruitmentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.recruitmentsystem.dto.auth.AuthResponse;
import com.company.recruitmentsystem.dto.auth.LoginRequest;
import com.company.recruitmentsystem.dto.auth.SignupRequest;
import com.company.recruitmentsystem.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signup(
			@RequestBody SignupRequest request) {
		AuthResponse response = authService.signup(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@RequestBody LoginRequest request) {
		AuthResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}
}
