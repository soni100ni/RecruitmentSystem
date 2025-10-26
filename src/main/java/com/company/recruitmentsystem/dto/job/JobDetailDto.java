package com.company.recruitmentsystem.dto.job;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JobDetailDto {
	private Long id;
	private String title;
	private String description;
	private String location;
	private String skillsRequired;
	private String companyName;
	private int totalApplications;
	private String postedByEmail;
	private LocalDateTime postedOn;
}
