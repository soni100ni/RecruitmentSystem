package com.company.recruitmentsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.recruitmentsystem.entity.Profile;
import com.company.recruitmentsystem.entity.User;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findByUser(User user);
}
