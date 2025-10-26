package com.company.recruitmentsystem.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.company.recruitmentsystem.exception.FileStorageException;

public class FileValidator {

	private static final List<String> ALLOWED_TYPES = Arrays.asList(
			"application/pdf",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
			"application/msword");

	public static void validateResume(MultipartFile file) {
		if (file.isEmpty()) {
			throw new FileStorageException("File is empty");
		}

		if (!ALLOWED_TYPES.contains(file.getContentType())) {
			throw new FileStorageException(
					"Invalid file type. Only PDF or DOCX allowed");
		}

		// Optional: limit file size (already in MultipartResolver, but extra
		// check)
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new FileStorageException("File size exceeds 5 MB limit");
		}
	}
}
