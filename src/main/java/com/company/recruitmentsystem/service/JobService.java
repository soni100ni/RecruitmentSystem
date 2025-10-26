package com.company.recruitmentsystem.service;

import java.util.List;

import com.company.recruitmentsystem.dto.job.JobCreateRequest;
import com.company.recruitmentsystem.dto.job.JobDetailDto;

public interface JobService {
	JobDetailDto createJob(JobCreateRequest request, String adminEmail);
	List<JobDetailDto> getAllJobs();
	JobDetailDto getJobById(Long jobId);
}
