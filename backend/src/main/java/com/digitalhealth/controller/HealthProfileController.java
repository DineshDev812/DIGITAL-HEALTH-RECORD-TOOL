package com.digitalhealth.controller;

import com.digitalhealth.dto.HealthProfileDTO;
import com.digitalhealth.service.HealthProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.digitalhealth.security.AccessService;

@RestController
@RequestMapping("/api/workers/{workerId}/health-profile")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class HealthProfileController {
    private final HealthProfileService healthProfileService;
    private final AccessService access;

    public HealthProfileController(HealthProfileService healthProfileService, AccessService access) {
        this.healthProfileService = healthProfileService;
        this.access=access;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HealthProfileDTO create(@PathVariable Long workerId, @Valid @RequestBody HealthProfileDTO dto) {
        access.workerOrAdmin(workerId); return healthProfileService.create(workerId, dto);
    }

    @GetMapping
    public HealthProfileDTO get(@PathVariable Long workerId) {
        access.clinicalAccess(workerId); return healthProfileService.get(workerId);
    }

    @PutMapping
    public HealthProfileDTO update(@PathVariable Long workerId, @Valid @RequestBody HealthProfileDTO dto) {
        access.workerOrAdmin(workerId); return healthProfileService.update(workerId, dto);
    }
}
