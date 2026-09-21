package com.digitalhealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_records", indexes = {@Index(name = "idx_record_worker_type_date", columnList = "worker_id,record_type,record_date")})
public class HealthRecord {
 @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "worker_id", nullable = false) private Worker worker;
 @Enumerated(EnumType.STRING) @Column(name="record_type", nullable=false, length=30) private RecordType recordType;
 @Column(name="record_date", nullable=false) private LocalDate recordDate;
 @Column(name="record_time") private LocalDateTime recordTime;
 @Column(nullable=false, length=160) private String title;
 @Column(length=160) private String provider;
 @Column(length=160) private String healthcareCenter;
 @Column(columnDefinition="TEXT") private String symptoms;
 @Column(columnDefinition="TEXT") private String observations;
 @Column(columnDefinition="TEXT") private String diagnosis;
 @Column(columnDefinition="TEXT") private String treatmentDetails;
 @Column(columnDefinition="TEXT") private String notes;
 @Column(length=160) private String medicineName;
 @Column(length=100) private String dosage;
 @Column(length=100) private String frequency;
 @Column(length=100) private String duration;
 @Column(columnDefinition="TEXT") private String instructions;
 @Column(name="follow_up_date") private LocalDate followUpDate;
 @Column(length=40) private String status;
 @Column(length=160) private String vaccineName;
 private Integer doseNumber;
 private LocalDate nextDoseDate;
 @Column(updatable=false, nullable=false) private LocalDateTime createdAt;
 @PrePersist void created(){ createdAt=LocalDateTime.now(); }
 public Long getId(){return id;} public void setId(Long x){id=x;} public Worker getWorker(){return worker;} public void setWorker(Worker x){worker=x;} public RecordType getRecordType(){return recordType;} public void setRecordType(RecordType x){recordType=x;} public LocalDate getRecordDate(){return recordDate;} public void setRecordDate(LocalDate x){recordDate=x;} public LocalDateTime getRecordTime(){return recordTime;} public void setRecordTime(LocalDateTime x){recordTime=x;} public String getTitle(){return title;} public void setTitle(String x){title=x;} public String getProvider(){return provider;} public void setProvider(String x){provider=x;} public String getHealthcareCenter(){return healthcareCenter;} public void setHealthcareCenter(String x){healthcareCenter=x;} public String getSymptoms(){return symptoms;} public void setSymptoms(String x){symptoms=x;} public String getObservations(){return observations;} public void setObservations(String x){observations=x;} public String getDiagnosis(){return diagnosis;} public void setDiagnosis(String x){diagnosis=x;} public String getTreatmentDetails(){return treatmentDetails;} public void setTreatmentDetails(String x){treatmentDetails=x;} public String getNotes(){return notes;} public void setNotes(String x){notes=x;} public String getMedicineName(){return medicineName;} public void setMedicineName(String x){medicineName=x;} public String getDosage(){return dosage;} public void setDosage(String x){dosage=x;} public String getFrequency(){return frequency;} public void setFrequency(String x){frequency=x;} public String getDuration(){return duration;} public void setDuration(String x){duration=x;} public String getInstructions(){return instructions;} public void setInstructions(String x){instructions=x;} public LocalDate getFollowUpDate(){return followUpDate;} public void setFollowUpDate(LocalDate x){followUpDate=x;} public String getStatus(){return status;} public void setStatus(String x){status=x;} public String getVaccineName(){return vaccineName;} public void setVaccineName(String x){vaccineName=x;} public Integer getDoseNumber(){return doseNumber;} public void setDoseNumber(Integer x){doseNumber=x;} public LocalDate getNextDoseDate(){return nextDoseDate;} public void setNextDoseDate(LocalDate x){nextDoseDate=x;} public LocalDateTime getCreatedAt(){return createdAt;}
}
