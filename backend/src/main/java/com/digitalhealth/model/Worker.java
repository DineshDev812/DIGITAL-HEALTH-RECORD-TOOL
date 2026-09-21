package com.digitalhealth.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "workers", uniqueConstraints = @UniqueConstraint(columnNames = "health_id"))
public class Worker {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "health_id", nullable = false, unique = true, length = 45)
    private String healthId;
    @Column(name = "full_name", nullable = false) private String fullName;
    @Column(name = "date_of_birth", nullable = false) private LocalDate dateOfBirth;
    @Column(nullable = false, length = 20) private String gender;
    @Column(name = "mobile_number", nullable = false, length = 15) private String mobileNumber;
    @Column(name = "native_state", nullable = false) private String nativeState;
    @Column(name = "native_district", nullable = false) private String nativeDistrict;
    @Column(name = "current_address", nullable = false, columnDefinition = "TEXT") private String currentAddress;
    @Column(nullable = false) private String occupation;
    @Column(name = "employer_name") private String employerName;
    @Column(name = "emergency_contact_name", nullable = false) private String emergencyContactName;
    @Column(name = "emergency_contact_number", nullable = false, length = 15) private String emergencyContactNumber;
    @OneToOne(mappedBy = "worker", fetch = FetchType.LAZY)
    private HealthProfile healthProfile;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getHealthId() { return healthId; } public void setHealthId(String healthId) { this.healthId = healthId; }
    public String getFullName() { return fullName; } public void setFullName(String fullName) { this.fullName = fullName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; } public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; } public void setGender(String gender) { this.gender = gender; }
    public String getMobileNumber() { return mobileNumber; } public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getNativeState() { return nativeState; } public void setNativeState(String nativeState) { this.nativeState = nativeState; }
    public String getNativeDistrict() { return nativeDistrict; } public void setNativeDistrict(String nativeDistrict) { this.nativeDistrict = nativeDistrict; }
    public String getCurrentAddress() { return currentAddress; } public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }
    public String getOccupation() { return occupation; } public void setOccupation(String occupation) { this.occupation = occupation; }
    public String getEmployerName() { return employerName; } public void setEmployerName(String employerName) { this.employerName = employerName; }
    public String getEmergencyContactName() { return emergencyContactName; } public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactNumber() { return emergencyContactNumber; } public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }
    public HealthProfile getHealthProfile() { return healthProfile; } public void setHealthProfile(HealthProfile healthProfile) { this.healthProfile = healthProfile; }
}
