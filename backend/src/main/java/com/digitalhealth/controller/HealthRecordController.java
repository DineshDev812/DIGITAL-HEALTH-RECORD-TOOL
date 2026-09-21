package com.digitalhealth.controller;
import com.digitalhealth.dto.HealthRecordDTO; import com.digitalhealth.model.RecordType; import com.digitalhealth.service.HealthRecordService; import com.digitalhealth.security.AccessService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/workers/{workerId}/records") @CrossOrigin(originPatterns={"http://localhost:*","http://127.0.0.1:*"}) public class HealthRecordController {
 private final HealthRecordService service; private final AccessService access; public HealthRecordController(HealthRecordService s,AccessService a){service=s;access=a;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public HealthRecordDTO create(@PathVariable Long workerId,@Valid @RequestBody HealthRecordDTO d){access.providerOrAdmin();access.clinicalAccess(workerId);return service.create(workerId,d);}
 @GetMapping public List<HealthRecordDTO> list(@PathVariable Long workerId,@RequestParam RecordType type){access.clinicalAccess(workerId);return service.list(workerId,type);}
 @PutMapping("/{id}") public HealthRecordDTO update(@PathVariable Long workerId,@PathVariable Long id,@Valid @RequestBody HealthRecordDTO d){access.providerOrAdmin();access.clinicalAccess(workerId);return service.update(workerId,id,d);}
}
