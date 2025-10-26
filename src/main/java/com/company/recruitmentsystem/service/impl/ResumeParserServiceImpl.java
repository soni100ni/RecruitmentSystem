package com.company.recruitmentsystem.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import com.company.recruitmentsystem.entity.Profile;
import com.company.recruitmentsystem.service.ResumeParserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResumeParserServiceImpl implements ResumeParserService {

	private static final String API_URL = "https://api.apilayer.com/resume_parser/upload";
	private static final String API_KEY = "0bWeisRWoLj3UdXt3MXMSMWptYFIpQfS";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public ResumeParserServiceImpl() {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public void parseResume(File resumeFile, Profile profile)
			throws IOException {

		// Read file bytes
		byte[] fileBytes = StreamUtils
				.copyToByteArray(new FileInputStream(resumeFile));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.set("apikey", API_KEY);

		HttpEntity<byte[]> entity = new HttpEntity<>(fileBytes, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(API_URL,
				entity, String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			JsonNode root = objectMapper.readTree(response.getBody());

			// Extract Education
			if (root.has("education")) {
				List<String> education = root.get("education")
						.findValues("name").stream().map(JsonNode::asText)
						.collect(Collectors.toList());
				profile.setEducation(String.join(", ", education));
			}

			// Extract Skills
			if (root.has("skills")) {
				List<String> skills = root.get("skills")
						.findValuesAsText("name").isEmpty()
								? root.get("skills").findValuesAsText("")
								: root.get("skills").findValuesAsText("");
				profile.setSkills(String.join(", ", skills));
			}

			// Extract Experience
			if (root.has("experience")) {
				List<String> experience = root.get("experience")
						.findValues("name").stream().map(JsonNode::asText)
						.collect(Collectors.toList());
				profile.setExperience(String.join(", ", experience));
			}

			// Extract Name
			if (root.has("name")) {
				String fullName = root.get("name").asText();
				String[] parts = fullName.split(" ");
				profile.setFirstName(parts[0]);
				profile.setLastName(parts.length > 1 ? parts[1] : "");
			}

			// Extract Phone
			if (root.has("phone")) {
				profile.setPhone(root.get("phone").asText());
			}
		}
	}
}
