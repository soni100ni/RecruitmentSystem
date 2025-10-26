package com.company.recruitmentsystem.dto.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobCreateRequest {

	@NotBlank(message = "Job title is required")
	private String title;

	@NotBlank(message = "Job description is required")
	private String description;

	private String location;

	private String skillsRequired; // Comma-separated skills

	@NotBlank(message = "Company name is required")
	private String companyName;
}
