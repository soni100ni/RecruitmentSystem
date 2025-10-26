package com.company.recruitmentsystem.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"applicants", "postedBy"})
@EqualsAndHashCode(exclude = {"applicants", "postedBy"})
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(length = 2000)
	private String description;

	private String location;
	private String skillsRequired;
	private String companyName;
	private int totalApplications;

	private LocalDateTime postedOn;

	// ✅ The recruiter who posted this job
	@ManyToOne
	@JoinColumn(name = "posted_by", nullable = false)
	@JsonIgnore
	private User postedBy;

	// ✅ All users who applied to this job
	@ManyToMany
	@JoinTable(name = "job_applicants", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@Builder.Default
	@JsonIgnore
	private Set<User> applicants = new HashSet<>();
}
