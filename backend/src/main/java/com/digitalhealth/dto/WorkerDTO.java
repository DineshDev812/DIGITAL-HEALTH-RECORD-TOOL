package com.digitalhealth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class WorkerDTO {
    private Long id;
    private String healthId;
    @NotBlank(message = "Full name is required") private String fullName;
    @NotNull(message = "Date of birth is required") @PastOrPresent(message = "Date of birth cannot be in the future") @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate dateOfBirth;
    @NotBlank(message = "Gender is required") private String gender;
    @NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number must contain 10 to 15 digits") private String mobileNumber;
    @NotBlank(message = "Native state is required") private String nativeState;
    @NotBlank(message = "Native district is required") private String nativeDistrict;
    @NotBlank(message = "Current Kerala address is required") private String currentAddress;
    @NotBlank(message = "Occupation is required") private String occupation;
    private String employerName;
    @NotBlank(message = "Emergency contact name is required") private String emergencyContactName;
    @NotBlank(message = "Emergency contact number is required") @Pattern(regexp = "^[0-9]{10,15}$", message = "Emergency contact number must contain 10 to 15 digits") private String emergencyContactNumber;
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
}
