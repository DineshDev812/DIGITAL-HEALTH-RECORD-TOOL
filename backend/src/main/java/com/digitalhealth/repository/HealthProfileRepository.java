package com.digitalhealth.repository;

import com.digitalhealth.model.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
    Optional<HealthProfile> findByWorkerId(Long workerId);
    boolean existsByWorkerId(Long workerId);
}
