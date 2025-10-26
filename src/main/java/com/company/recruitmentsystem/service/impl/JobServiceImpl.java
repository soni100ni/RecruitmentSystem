package com.company.recruitmentsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.recruitmentsystem.dto.job.JobCreateRequest;
import com.company.recruitmentsystem.dto.job.JobDetailDto;
import com.company.recruitmentsystem.entity.Job;
import com.company.recruitmentsystem.entity.User;
import com.company.recruitmentsystem.exception.ResourceNotFoundException;
import com.company.recruitmentsystem.repository.JobRepository;
import com.company.recruitmentsystem.repository.UserRepository;
import com.company.recruitmentsystem.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;
	private final UserRepository userRepository;

	@Override
	public JobDetailDto createJob(JobCreateRequest request, String adminEmail) {
		User admin = userRepository.findByEmail(adminEmail).orElseThrow(
				() -> new ResourceNotFoundException("Admin not found"));

		Job job = Job.builder().title(request.getTitle())
				.description(request.getDescription())
				.location(request.getLocation())
				.skillsRequired(request.getSkillsRequired())
				.companyName(request.getCompanyName())
				.postedOn(LocalDateTime.now()).totalApplications(0)
				.postedBy(admin).build();

		jobRepository.save(job);
		return mapToDto(job);
	}

	@Override
	public List<JobDetailDto> getAllJobs() {
		return jobRepository.findAll().stream().map(this::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public JobDetailDto getJobById(Long jobId) {
		Job job = jobRepository.findById(jobId).orElseThrow(
				() -> new ResourceNotFoundException("Job not found"));
		return mapToDto(job);
	}

	private JobDetailDto mapToDto(Job job) {
		JobDetailDto dto = new JobDetailDto();
		dto.setId(job.getId());
		dto.setTitle(job.getTitle());
		dto.setDescription(job.getDescription());
		dto.setLocation(job.getLocation());
		dto.setSkillsRequired(job.getSkillsRequired());
		dto.setCompanyName(job.getCompanyName());
		dto.setTotalApplications(job.getTotalApplications());
		dto.setPostedByEmail(job.getPostedBy().getEmail());
		dto.setPostedOn(job.getPostedOn());
		return dto;
	}
}
