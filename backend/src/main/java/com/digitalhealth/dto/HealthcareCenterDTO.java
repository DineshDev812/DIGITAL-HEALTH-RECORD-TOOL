package com.digitalhealth.dto;
import jakarta.validation.constraints.*;
public class HealthcareCenterDTO { public Long id; @NotBlank public String name; @NotBlank public String address; public String district; public String contactNumber; public String services; public String operatingHours; public String status="ACTIVE"; }
