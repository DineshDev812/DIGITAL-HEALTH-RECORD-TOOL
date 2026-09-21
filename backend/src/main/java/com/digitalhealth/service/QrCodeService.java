package com.digitalhealth.service;

import com.digitalhealth.model.Worker;
import com.digitalhealth.repository.HealthProfileRepository;
import com.digitalhealth.repository.WorkerRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class QrCodeService {
    private static final int QR_CODE_SIZE = 300;

    private final WorkerRepository workerRepository;
    private final HealthProfileRepository healthProfileRepository;

    public QrCodeService(WorkerRepository workerRepository, HealthProfileRepository healthProfileRepository) {
        this.workerRepository = workerRepository;
        this.healthProfileRepository = healthProfileRepository;
    }

    public byte[] generateForWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Worker not found"));

        String bloodGroup = healthProfileRepository.findByWorkerId(workerId)
                .map(profile -> profile.getBloodGroup())
                .orElse("Not provided");
        String qrContent = "Health ID: " + worker.getHealthId()
                + "\nName: " + worker.getFullName()
                + "\nBlood Group: " + bloodGroup;
        return generatePng(qrContent);
    }

    private byte[] generatePng(String healthId) {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            BitMatrix matrix = new QRCodeWriter().encode(
                    healthId,
                    BarcodeFormat.QR_CODE,
                    QR_CODE_SIZE,
                    QR_CODE_SIZE,
                    Map.of(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name())
            );
            MatrixToImageWriter.writeToStream(matrix, "PNG", output);
            return output.toByteArray();
        } catch (WriterException | IOException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to generate QR code", exception);
        }
    }
}
