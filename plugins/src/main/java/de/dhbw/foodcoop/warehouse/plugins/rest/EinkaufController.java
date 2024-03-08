package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import de.dhbw.foodcoop.warehouse.application.einkauf.EinkaufService;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

@RestController
public class EinkaufController {
	
    private final EinkaufService einkaufService;
    
    @Autowired
    public EinkaufController(EinkaufService einkaufService) {
    	this.einkaufService = einkaufService;
    }
    
    @GetMapping(value = "/einkaufe")
    public List<EinkaufEntity> einkäufe() {
    	return einkaufService.all();
    }
}
