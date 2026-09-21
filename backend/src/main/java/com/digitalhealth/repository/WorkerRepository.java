package com.digitalhealth.repository;

import com.digitalhealth.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> { }
