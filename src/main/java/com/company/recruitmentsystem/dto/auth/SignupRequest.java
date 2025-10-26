package com.company.recruitmentsystem.dto.auth;

import com.company.recruitmentsystem.entity.UserType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class SignupRequest {

	@NotBlank(message = "Name is required")
	private String name; // <-- add this

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	private String password;

	private UserType userType;
}
