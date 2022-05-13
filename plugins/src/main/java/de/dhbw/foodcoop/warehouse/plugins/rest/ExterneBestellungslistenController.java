package de.dhbw.foodcoop.warehouse.plugins.rest;


import de.dhbw.foodcoop.warehouse.application.bestellungsliste.ExterneBestellungslisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class ExterneBestellungslistenController {
    private final ExterneBestellungslisteService service;

    @Autowired
    public ExterneBestellungslistenController(ExterneBestellungslisteService service) {
        this.service = service;
    }

    @GetMapping(value = "/externeliste")
    public ResponseEntity<StreamingResponseBody> one() throws IOException {
        String fileName = service.getFileName();
        byte[] pdfInBytes = service.createExterneListe();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfInBytes);
        StreamingResponseBody responseBody = outputStream -> {

            int numberOfBytesToWrite;
            byte[] data = new byte[1024];
            while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, numberOfBytesToWrite);
            }

            inputStream.close();
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(responseBody);
    }

    @GetMapping(value = "/externeliste/gebinde")
    public ResponseEntity<StreamingResponseBody> oneGebinde() throws IOException {
        String fileName = service.getFileName();
        byte[] pdfInBytes = service.createExterneListeGebinde();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfInBytes);
        StreamingResponseBody responseBody = outputStream -> {

            int numberOfBytesToWrite;
            byte[] data = new byte[1024];
            while ((numberOfBytesToWrite = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, numberOfBytesToWrite);
            }

            inputStream.close();
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(responseBody);
    }

}
