package com.company.recruitmentsystem.service;

import org.springframework.web.multipart.MultipartFile;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;

public interface ApplicantService {
	ProfileDto uploadResume(MultipartFile file, String email);
	ProfileDto getProfile(String email);
	ProfileDto updateProfile(String email, ProfileDto dto);
	void applyToJob(String email, Long jobId);

}
