package com.digitalhealth.controller;

import com.digitalhealth.service.QrCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkerControllerQrTest {

    @Test
    void returnsQrCodeAsPng() throws Exception {
        byte[] png = {1, 2, 3};
        QrCodeService qrCodeService = new QrCodeService(null, null) {
            @Override
            public byte[] generateForWorker(Long workerId) {
                return png;
            }
        };
        WorkerController controller = new WorkerController(null, qrCodeService);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/api/workers/1/qr"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(png));
    }
}
