package com.company.recruitmentsystem.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.recruitmentsystem.dto.auth.AuthResponse;
import com.company.recruitmentsystem.dto.auth.LoginRequest;
import com.company.recruitmentsystem.dto.auth.SignupRequest;
import com.company.recruitmentsystem.entity.User;
import com.company.recruitmentsystem.exception.ResourceNotFoundException;
import com.company.recruitmentsystem.repository.UserRepository;
import com.company.recruitmentsystem.secuity.JwtService;
import com.company.recruitmentsystem.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public AuthResponse signup(SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}

		// Set all required fields including name
		User user = User.builder().name(request.getName()) // <-- add this
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.userType(request.getUserType()).build();

		userRepository.save(user);

		String token = jwtService.generateToken(user.getEmail());
		return new AuthResponse(token);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(),
						request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
				() -> new ResourceNotFoundException("User not found"));

		String token = jwtService.generateToken(user.getEmail());
		return new AuthResponse(token);
	}
}
