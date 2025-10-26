package com.company.recruitmentsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;
import com.company.recruitmentsystem.dto.job.JobCreateRequest;
import com.company.recruitmentsystem.dto.job.JobDetailDto;
import com.company.recruitmentsystem.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/jobs")
	public ResponseEntity<?> createJob(@RequestBody JobCreateRequest request) {
		String adminEmail = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		return ResponseEntity.ok(adminService.createJob(request, adminEmail));
	}

	@GetMapping("/jobs")
	public ResponseEntity<List<JobDetailDto>> getAllJobs() {
		return ResponseEntity.ok(adminService.getAllJobs());
	}

	@GetMapping("/jobs/{id}")
	public ResponseEntity<JobDetailDto> getJobById(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.getJobById(id));
	}

	// ✅ Get all applicants
	@GetMapping("/applicants")
	public ResponseEntity<List<ProfileDto>> getAllApplicants() {
		return ResponseEntity.ok(adminService.getAllApplicants());
	}

	// ✅ Get single applicant by ID
	@GetMapping("/applicant/{id}")
	public ResponseEntity<ProfileDto> getApplicantById(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.getApplicantById(id));
	}
}
