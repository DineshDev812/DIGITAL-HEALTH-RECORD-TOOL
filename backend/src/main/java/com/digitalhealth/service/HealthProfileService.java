package com.digitalhealth.service;

import com.digitalhealth.dto.HealthProfileDTO;
import com.digitalhealth.model.HealthProfile;
import com.digitalhealth.model.Worker;
import com.digitalhealth.repository.HealthProfileRepository;
import com.digitalhealth.repository.WorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HealthProfileService {
    private final HealthProfileRepository healthProfileRepository;
    private final WorkerRepository workerRepository;

    public HealthProfileService(HealthProfileRepository healthProfileRepository, WorkerRepository workerRepository) {
        this.healthProfileRepository = healthProfileRepository;
        this.workerRepository = workerRepository;
    }

    public HealthProfileDTO create(Long workerId, HealthProfileDTO dto) {
        Worker worker = findWorker(workerId);
        if (healthProfileRepository.existsByWorkerId(workerId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Health profile already exists");
        }

        HealthProfile profile = apply(dto, new HealthProfile());
        profile.setWorker(worker);
        return toDto(healthProfileRepository.save(profile));
    }

    public HealthProfileDTO get(Long workerId) {
        findWorker(workerId);
        return toDto(findProfile(workerId));
    }

    public HealthProfileDTO update(Long workerId, HealthProfileDTO dto) {
        findWorker(workerId);
        HealthProfile profile = apply(dto, findProfile(workerId));
        return toDto(healthProfileRepository.save(profile));
    }

    private Worker findWorker(Long workerId) {
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));
    }

    private HealthProfile findProfile(Long workerId) {
        return healthProfileRepository.findByWorkerId(workerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Health profile not found"));
    }

    private HealthProfile apply(HealthProfileDTO dto, HealthProfile profile) {
        profile.setBloodGroup(dto.getBloodGroup());
        profile.setHeight(dto.getHeight());
        profile.setWeight(dto.getWeight());
        profile.setAllergies(dto.getAllergies());
        profile.setExistingDiseases(dto.getExistingDiseases());
        profile.setChronicConditions(dto.getChronicConditions());
        profile.setCurrentMedications(dto.getCurrentMedications());
        profile.setEmergencyMedicalNotes(dto.getEmergencyMedicalNotes());
        profile.setBasicHealthNotes(dto.getBasicHealthNotes());
        return profile;
    }

    private HealthProfileDTO toDto(HealthProfile profile) {
        HealthProfileDTO dto = new HealthProfileDTO();
        dto.setId(profile.getId());
        dto.setBloodGroup(profile.getBloodGroup());
        dto.setHeight(profile.getHeight());
        dto.setWeight(profile.getWeight());
        dto.setAllergies(profile.getAllergies());
        dto.setExistingDiseases(profile.getExistingDiseases());
        dto.setChronicConditions(profile.getChronicConditions());
        dto.setCurrentMedications(profile.getCurrentMedications());
        dto.setEmergencyMedicalNotes(profile.getEmergencyMedicalNotes());
        dto.setBasicHealthNotes(profile.getBasicHealthNotes());
        return dto;
    }
}
