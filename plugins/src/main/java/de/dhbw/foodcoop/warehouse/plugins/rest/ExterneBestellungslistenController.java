package de.dhbw.foodcoop.warehouse.plugins.rest;


import de.dhbw.foodcoop.warehouse.application.bestellungsliste.ExterneBestellungslisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        File file = service.createExterneListe();
        StreamingResponseBody responseBody = outputStream -> Files.copy(file.toPath(), outputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(responseBody);
    }

}
