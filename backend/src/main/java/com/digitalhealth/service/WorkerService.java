package com.digitalhealth.service;

import com.digitalhealth.dto.WorkerDTO;
import com.digitalhealth.model.Worker;
import com.digitalhealth.repository.WorkerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.Year;
import java.util.UUID;

@Service
public class WorkerService {
    private final WorkerRepository workerRepository;
    public WorkerService(WorkerRepository workerRepository) { this.workerRepository = workerRepository; }
    public WorkerDTO create(WorkerDTO dto) {
        Worker worker = map(dto, new Worker());
        // A temporary unique value satisfies the non-null database constraint until its numeric id is assigned.
        worker.setHealthId("PENDING-" + UUID.randomUUID());
        Worker saved = workerRepository.save(worker);
        saved.setHealthId(String.format("MWK-%d-%04d", Year.now().getValue(), saved.getId()));
        return toDto(workerRepository.save(saved));
    }
    public WorkerDTO getById(Long id) { return toDto(find(id)); }
    public java.util.List<WorkerDTO> list() { return workerRepository.findAll().stream().map(this::toDto).toList(); }
    public WorkerDTO update(Long id, WorkerDTO dto) {
        Worker worker = map(dto, find(id));
        return toDto(workerRepository.save(worker));
    }
    public void delete(Long id) { workerRepository.delete(find(id)); }
    private Worker find(Long id) { return workerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found")); }
    private Worker map(WorkerDTO dto, Worker worker) {
        worker.setFullName(dto.getFullName()); worker.setDateOfBirth(dto.getDateOfBirth()); worker.setGender(dto.getGender()); worker.setMobileNumber(dto.getMobileNumber());
        worker.setNativeState(dto.getNativeState()); worker.setNativeDistrict(dto.getNativeDistrict()); worker.setCurrentAddress(dto.getCurrentAddress()); worker.setOccupation(dto.getOccupation());
        worker.setEmployerName(dto.getEmployerName()); worker.setEmergencyContactName(dto.getEmergencyContactName()); worker.setEmergencyContactNumber(dto.getEmergencyContactNumber()); return worker;
    }
    private WorkerDTO toDto(Worker w) {
        WorkerDTO d = new WorkerDTO(); d.setId(w.getId()); d.setHealthId(w.getHealthId()); d.setFullName(w.getFullName()); d.setDateOfBirth(w.getDateOfBirth()); d.setGender(w.getGender()); d.setMobileNumber(w.getMobileNumber());
        d.setNativeState(w.getNativeState()); d.setNativeDistrict(w.getNativeDistrict()); d.setCurrentAddress(w.getCurrentAddress()); d.setOccupation(w.getOccupation()); d.setEmployerName(w.getEmployerName());
        d.setEmergencyContactName(w.getEmergencyContactName()); d.setEmergencyContactNumber(w.getEmergencyContactNumber()); return d;
    }
}
