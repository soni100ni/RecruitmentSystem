package com.company.recruitmentsystem.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;
import com.company.recruitmentsystem.dto.job.JobCreateRequest;
import com.company.recruitmentsystem.dto.job.JobDetailDto;
import com.company.recruitmentsystem.entity.Job;
import com.company.recruitmentsystem.entity.Profile;
import com.company.recruitmentsystem.entity.User;
import com.company.recruitmentsystem.exception.ResourceNotFoundException;
import com.company.recruitmentsystem.repository.JobRepository;
import com.company.recruitmentsystem.repository.ProfileRepository;
import com.company.recruitmentsystem.repository.UserRepository;
import com.company.recruitmentsystem.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final JobRepository jobRepository;
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;

	// ------------------- Job Methods -------------------

	@Override
	public JobDetailDto createJob(JobCreateRequest dto, String adminEmail) {
		User admin = userRepository.findByEmail(adminEmail).orElseThrow(
				() -> new ResourceNotFoundException("Admin not found"));

		Job job = Job.builder().title(dto.getTitle())
				.description(dto.getDescription()).location(dto.getLocation())
				.skillsRequired(dto.getSkillsRequired())
				.companyName(dto.getCompanyName()).postedOn(LocalDateTime.now())
				.totalApplications(0).postedBy(admin).build();

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
		dto.setPostedByEmail(job.getPostedBy().getEmail());
		dto.setPostedOn(job.getPostedOn());
		dto.setTotalApplications(job.getTotalApplications());
		return dto;
	}

	// ------------------- Applicant Methods -------------------

	@Override
	public List<ProfileDto> getAllApplicants() {
		return profileRepository.findAll().stream().map(this::mapProfileToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ProfileDto getApplicantById(Long applicantId) {
		Profile profile = profileRepository.findById(applicantId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Applicant profile not found"));
		return mapProfileToDto(profile);
	}

	private ProfileDto mapProfileToDto(Profile profile) {
		ProfileDto dto = new ProfileDto();
		dto.setFirstName(profile.getFirstName());
		dto.setLastName(profile.getLastName());
		dto.setPhone(profile.getPhone());
		dto.setSkills(profile.getSkills());
		dto.setEducation(profile.getEducation());
		dto.setExperience(profile.getExperience());
		dto.setResumeFilePath(profile.getResumeFilePath());
		return dto;
	}
}
