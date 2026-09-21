package com.digitalhealth.service;

import com.digitalhealth.model.Worker;
import com.digitalhealth.repository.HealthProfileRepository;
import com.digitalhealth.repository.WorkerRepository;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.awt.image.BufferedImage;
import java.util.Optional;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QrCodeServiceTest {

    @Test
    void generatesScannableQrCodeContainingOnlyTheHealthId() throws Exception {
        Worker worker = new Worker();
        worker.setFullName("Arun Kumar");
        worker.setHealthId("MWK-2026-0001");
        WorkerRepository workerRepository = repositoryReturning(Optional.of(worker));

        byte[] png = new QrCodeService(workerRepository, healthProfileRepository()).generateForWorker(1L);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(png));
        Result decoded = new MultiFormatReader().decode(new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(image))));

        assertEquals("Health ID: MWK-2026-0001\nName: Arun Kumar\nBlood Group: Not provided", decoded.getText());
    }

    @Test
    void returnsNotFoundForAnUnknownWorker() {
        WorkerRepository workerRepository = repositoryReturning(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> new QrCodeService(workerRepository, healthProfileRepository()).generateForWorker(99L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    private WorkerRepository repositoryReturning(Optional<Worker> worker) {
        return (WorkerRepository) Proxy.newProxyInstance(
                WorkerRepository.class.getClassLoader(),
                new Class<?>[]{WorkerRepository.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("findById")) {
                        return worker;
                    }
                    throw new UnsupportedOperationException(method.getName());
                }
        );
    }

    private HealthProfileRepository healthProfileRepository() {
        return (HealthProfileRepository) Proxy.newProxyInstance(
                HealthProfileRepository.class.getClassLoader(),
                new Class<?>[]{HealthProfileRepository.class},
                (proxy, method, arguments) -> {
                    if (method.getName().equals("findByWorkerId")) return Optional.empty();
                    throw new UnsupportedOperationException(method.getName());
                }
        );
    }
}
