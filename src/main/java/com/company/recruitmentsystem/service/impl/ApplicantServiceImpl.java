package com.company.recruitmentsystem.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.company.recruitmentsystem.dto.applicant.ProfileDto;
import com.company.recruitmentsystem.entity.Job;
import com.company.recruitmentsystem.entity.Profile;
import com.company.recruitmentsystem.entity.User;
import com.company.recruitmentsystem.exception.FileStorageException;
import com.company.recruitmentsystem.exception.ResourceNotFoundException;
import com.company.recruitmentsystem.repository.JobRepository;
import com.company.recruitmentsystem.repository.ProfileRepository;
import com.company.recruitmentsystem.repository.UserRepository;
import com.company.recruitmentsystem.service.ApplicantService;
import com.company.recruitmentsystem.service.ResumeParserService;
import com.company.recruitmentsystem.util.FileValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final JobRepository jobRepository;
	private final ResumeParserService resumeParserService;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public ProfileDto uploadResume(MultipartFile file, String email) {
		FileValidator.validateResume(file);

		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("User not found"));

		Profile profile = profileRepository.findByUser(user)
				.orElse(Profile.builder().user(user).firstName("").lastName("")
						.phone("").skills("").education("").experience("")
						.resumeFilePath("").build());

		try {
			Path uploadPath = Paths.get(uploadDir, "resumes");
			if (!Files.exists(uploadPath))
				Files.createDirectories(uploadPath);

			String filename = email + "_"
					+ StringUtils.cleanPath(file.getOriginalFilename());
			Path filePath = uploadPath.resolve(filename);
			file.transferTo(filePath.toFile());

			profile.setResumeFilePath("uploads/resumes/" + filename);

			// Call third-party API to parse resume
			// Call third-party API to parse resume
			resumeParserService.parseResume(filePath.toFile(), profile);
			profileRepository.save(profile);
			return mapToDto(profile);

		} catch (IOException e) {
			throw new FileStorageException(
					"Could not store file: " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public ProfileDto getProfile(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("User not found"));
		Profile profile = profileRepository.findByUser(user).orElseThrow(
				() -> new ResourceNotFoundException("Profile not found"));
		return mapToDto(profile);
	}

	@Override
	public ProfileDto updateProfile(String email, ProfileDto dto) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("User not found"));
		Profile profile = profileRepository.findByUser(user).orElse(Profile
				.builder().user(user).firstName("").lastName("").build());

		profile.setFirstName(dto.getFirstName());
		profile.setLastName(dto.getLastName());
		profile.setPhone(dto.getPhone());
		profile.setSkills(dto.getSkills());
		profile.setEducation(dto.getEducation());
		profile.setExperience(dto.getExperience());
		profile.setResumeFilePath(dto.getResumeFilePath());

		profileRepository.save(profile);
		return mapToDto(profile);
	}

	@Override
	public void applyToJob(String email, Long jobId) {
		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("User not found"));
		Job job = jobRepository.findById(jobId).orElseThrow(
				() -> new ResourceNotFoundException("Job not found"));

		job.getApplicants().add(user); // ✅ Now valid
		job.setTotalApplications(job.getApplicants().size()); // Update count
		jobRepository.save(job);
	}

	private ProfileDto mapToDto(Profile profile) {
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
