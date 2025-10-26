package com.company.recruitmentsystem.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"profile", "appliedJobs", "postedJobs"})
@EqualsAndHashCode(exclude = {"profile", "appliedJobs", "postedJobs"})
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserType userType;

	private String profileHeadline;
	private String address;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Profile profile;

	// ✅ One user can post many jobs
	@OneToMany(mappedBy = "postedBy")
	@JsonIgnore
	private Set<Job> postedJobs = new HashSet<>();

	// ✅ One user can apply to many jobs
	@ManyToMany(mappedBy = "applicants")
	@JsonIgnore
	private Set<Job> appliedJobs = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(() -> "ROLE_" + this.userType.name());
	}

	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
