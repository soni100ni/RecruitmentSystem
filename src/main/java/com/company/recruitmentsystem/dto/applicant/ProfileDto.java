package com.company.recruitmentsystem.dto.applicant;

import lombok.Data;

@Data
public class ProfileDto {
	private String firstName;
	private String lastName;
	private String phone;
	private String skills;
	private String education;
	private String experience;
	private String resumeFilePath; // URL or path to resume
}
