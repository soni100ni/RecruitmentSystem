package com.company.recruitmentsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.recruitmentsystem.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
	List<Job> findByTitleContainingIgnoreCase(String title);
	List<Job> findBySkillsRequiredContainingIgnoreCase(String skill);
}
