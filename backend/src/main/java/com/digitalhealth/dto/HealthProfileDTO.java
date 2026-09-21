package com.digitalhealth.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class HealthProfileDTO {
    private Long id;

    @NotBlank(message = "Blood group is required")
    private String bloodGroup;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Height must be positive")
    private BigDecimal height;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be positive")
    private BigDecimal weight;

    private String allergies;
    private String existingDiseases;
    private String chronicConditions;
    private String currentMedications;
    private String emergencyMedicalNotes;
    private String basicHealthNotes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
