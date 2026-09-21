package com.digitalhealth.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "health_profiles", uniqueConstraints = @UniqueConstraint(columnNames = "worker_id"))
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false, unique = true)
    private Worker worker;

    @Column(name = "blood_group", nullable = false, length = 5)
    private String bloodGroup;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal height;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal weight;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "existing_diseases", columnDefinition = "TEXT")
    private String existingDiseases;

    @Column(name = "chronic_conditions", columnDefinition = "TEXT")
    private String chronicConditions;

    @Column(name = "current_medications", columnDefinition = "TEXT")
    private String currentMedications;

    @Column(name = "emergency_medical_notes", columnDefinition = "TEXT")
    private String emergencyMedicalNotes;

    @Column(name = "basic_health_notes", columnDefinition = "TEXT")
    private String basicHealthNotes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Worker getWorker() { return worker; }
    public void setWorker(Worker worker) { this.worker = worker; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public BigDecimal getHeight() { return height; }
    public void setHeight(BigDecimal height) { this.height = height; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getExistingDiseases() { return existingDiseases; }
    public void setExistingDiseases(String existingDiseases) { this.existingDiseases = existingDiseases; }
    public String getChronicConditions() { return chronicConditions; }
    public void setChronicConditions(String chronicConditions) { this.chronicConditions = chronicConditions; }
    public String getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }
    public String getEmergencyMedicalNotes() { return emergencyMedicalNotes; }
    public void setEmergencyMedicalNotes(String emergencyMedicalNotes) { this.emergencyMedicalNotes = emergencyMedicalNotes; }
    public String getBasicHealthNotes() { return basicHealthNotes; }
    public void setBasicHealthNotes(String basicHealthNotes) { this.basicHealthNotes = basicHealthNotes; }
}
