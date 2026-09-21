package com.digitalhealth.repository;
import com.digitalhealth.model.MedicalReport; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface MedicalReportRepository extends JpaRepository<MedicalReport,Long>{ List<MedicalReport> findByWorkerIdOrderByReportDateDesc(Long workerId); }
