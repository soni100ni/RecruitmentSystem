package com.company.recruitmentsystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.recruitmentsystem.dto.job.JobDetailDto;
import com.company.recruitmentsystem.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

	private final JobService jobService;

	@GetMapping
	public ResponseEntity<List<JobDetailDto>> getAllJobs() {
		List<JobDetailDto> jobs = jobService.getAllJobs();
		return ResponseEntity.ok(jobs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobDetailDto> getJobById(@PathVariable Long id) {
		JobDetailDto dto = jobService.getJobById(id);
		return ResponseEntity.ok(dto);
	}
}
