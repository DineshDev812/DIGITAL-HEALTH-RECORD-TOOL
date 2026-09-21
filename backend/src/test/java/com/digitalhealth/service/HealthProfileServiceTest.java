package com.digitalhealth.service;

import com.digitalhealth.dto.HealthProfileDTO;
import com.digitalhealth.model.HealthProfile;
import com.digitalhealth.model.Worker;
import com.digitalhealth.repository.HealthProfileRepository;
import com.digitalhealth.repository.WorkerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HealthProfileServiceTest {

    @Test
    void createsReadsAndUpdatesOneProfileForAnExistingWorker() {
        Worker worker = worker(1L);
        Map<Long, HealthProfile> profiles = new HashMap<>();
        HealthProfileService service = new HealthProfileService(profileRepository(profiles), workerRepository(worker));

        HealthProfileDTO created = service.create(1L, profile("O+", "172.5", "70.2"));
        HealthProfileDTO read = service.get(1L);
        HealthProfileDTO updated = service.update(1L, profile("A+", "173", "71"));

        assertEquals("O+", created.getBloodGroup());
        assertEquals("O+", read.getBloodGroup());
        assertEquals("A+", updated.getBloodGroup());
        assertEquals(new BigDecimal("71"), updated.getWeight());
    }

    @Test
    void rejectsDuplicateProfileAndUnknownWorker() {
        Worker worker = worker(1L);
        Map<Long, HealthProfile> profiles = new HashMap<>();
        HealthProfileService service = new HealthProfileService(profileRepository(profiles), workerRepository(worker));
        service.create(1L, profile("O+", "172", "70"));

        ResponseStatusException duplicate = assertThrows(ResponseStatusException.class,
                () -> service.create(1L, profile("O+", "172", "70")));
        ResponseStatusException missing = assertThrows(ResponseStatusException.class,
                () -> service.get(99L));

        assertEquals(HttpStatus.CONFLICT, duplicate.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, missing.getStatusCode());
    }

    private Worker worker(Long id) {
        Worker worker = new Worker();
        worker.setId(id);
        return worker;
    }

    private HealthProfileDTO profile(String bloodGroup, String height, String weight) {
        HealthProfileDTO dto = new HealthProfileDTO();
        dto.setBloodGroup(bloodGroup);
        dto.setHeight(new BigDecimal(height));
        dto.setWeight(new BigDecimal(weight));
        dto.setAllergies("None");
        return dto;
    }

    private WorkerRepository workerRepository(Worker worker) {
        return (WorkerRepository) Proxy.newProxyInstance(
                WorkerRepository.class.getClassLoader(),
                new Class<?>[]{WorkerRepository.class},
                (proxy, method, arguments) -> method.getName().equals("findById") && worker.getId().equals(arguments[0])
                        ? Optional.of(worker) : Optional.empty());
    }

    private HealthProfileRepository profileRepository(Map<Long, HealthProfile> profiles) {
        return (HealthProfileRepository) Proxy.newProxyInstance(
                HealthProfileRepository.class.getClassLoader(),
                new Class<?>[]{HealthProfileRepository.class},
                (proxy, method, arguments) -> {
                    return switch (method.getName()) {
                        case "findByWorkerId" -> Optional.ofNullable(profiles.get(arguments[0]));
                        case "existsByWorkerId" -> profiles.containsKey(arguments[0]);
                        case "save" -> {
                            HealthProfile profile = (HealthProfile) arguments[0];
                            if (profile.getId() == null) profile.setId(1L);
                            profiles.put(profile.getWorker().getId(), profile);
                            yield profile;
                        }
                        default -> throw new UnsupportedOperationException(method.getName());
                    };
                });
    }
}
