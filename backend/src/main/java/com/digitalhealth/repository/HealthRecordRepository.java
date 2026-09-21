package com.digitalhealth.repository;
import com.digitalhealth.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface HealthRecordRepository extends JpaRepository<HealthRecord,Long>{ List<HealthRecord> findByWorkerIdAndRecordTypeOrderByRecordDateDesc(Long workerId, RecordType type); List<HealthRecord> findTop5ByWorkerIdOrderByRecordDateDesc(Long workerId); }
