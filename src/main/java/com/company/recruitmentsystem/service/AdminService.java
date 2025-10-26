package com.company.recruitmentsystem.service;

import java.util.List;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;
import com.company.recruitmentsystem.dto.job.JobCreateRequest;
import com.company.recruitmentsystem.dto.job.JobDetailDto;

public interface AdminService {

	// Job-related methods
	JobDetailDto createJob(JobCreateRequest dto, String adminEmail);

	List<JobDetailDto> getAllJobs();

	JobDetailDto getJobById(Long jobId);

	// Applicant-related methods
	List<ProfileDto> getAllApplicants();

	ProfileDto getApplicantById(Long applicantId);
}
