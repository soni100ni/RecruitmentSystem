package com.company.recruitmentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;
import com.company.recruitmentsystem.service.ApplicantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applicant")
@RequiredArgsConstructor
public class ApplicantController {

	private final ApplicantService applicantService;

	@GetMapping("/profile")
	public ResponseEntity<ProfileDto> getProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return ResponseEntity.ok(applicantService.getProfile(email));
	}

	@PutMapping("/profile")
	public ResponseEntity<ProfileDto> updateProfile(
			@RequestBody ProfileDto dto) {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		return ResponseEntity.ok(applicantService.updateProfile(email, dto));
	}

	// ✅ Upload resume (PDF/DOCX)
	@PostMapping("/uploadResume")
	public ResponseEntity<ProfileDto> uploadResume(
			@RequestParam("file") MultipartFile file) {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		ProfileDto updatedProfile = applicantService.uploadResume(file, email);
		return ResponseEntity.ok(updatedProfile);
	}

	// ✅ Apply to job
	@PostMapping("/jobs/apply")
	public ResponseEntity<String> applyToJob(@RequestParam Long jobId) {
		String email = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		applicantService.applyToJob(email, jobId);
		return ResponseEntity.ok("Applied successfully!");
	}
}
