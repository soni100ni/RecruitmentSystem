package com.company.recruitmentsystem.service;

import java.io.File;
import java.io.IOException;

import com.company.recruitmentsystem.entity.Profile;

public interface ResumeParserService {
	void parseResume(File resumeFile, Profile profile) throws IOException;

}
