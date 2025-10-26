package com.company.recruitmentsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Basic applicant info
	private String firstName;
	private String lastName;
	private String email; // optional, can duplicate User.email
	private String phone;

	// Resume & professional info
	private String resumeFilePath;
	private String skills;
	private String education;
	private String experience;

	// Link back to User
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
