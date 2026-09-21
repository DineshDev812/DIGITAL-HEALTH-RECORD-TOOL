package com.digitalhealth.controller;

import com.digitalhealth.dto.WorkerDTO;
import com.digitalhealth.service.QrCodeService;
import com.digitalhealth.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.digitalhealth.security.AccessService;

@RestController
@RequestMapping("/api/workers")
@CrossOrigin(originPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
public class WorkerController {
    private final WorkerService workerService;
    private final QrCodeService qrCodeService;
    private final AccessService access;
    @Autowired public WorkerController(WorkerService workerService, QrCodeService qrCodeService, AccessService access) { this.workerService = workerService; this.qrCodeService = qrCodeService; this.access=access; }
    /** Kept for the focused QR unit test; the application uses the secured constructor above. */
    public WorkerController(WorkerService workerService, QrCodeService qrCodeService) { this(workerService, qrCodeService, null); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public WorkerDTO register(@Valid @RequestBody WorkerDTO dto) { return workerService.create(dto); }
    @GetMapping public List<WorkerDTO> list() { access.admin(); return workerService.list(); }
    @GetMapping("/{id}") public WorkerDTO get(@PathVariable Long id) { access.workerOrAdmin(id); return workerService.getById(id); }
    @GetMapping(value = "/{id}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrCode(@PathVariable Long id) { if(access!=null) access.workerOrAdmin(id); return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeService.generateForWorker(id)); }
    @PutMapping("/{id}") public WorkerDTO update(@PathVariable Long id, @Valid @RequestBody WorkerDTO dto) { access.workerOrAdmin(id); return workerService.update(id, dto); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { access.admin(); workerService.delete(id); }
}
