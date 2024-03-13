package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.einkauf.EinkaufService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

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
    
    @GetMapping(value = "/einkaufe/create/EinkaufBestellungVergleich")
    public EinkaufBestellungVergleich getEBVFromData(@RequestBody BestellungEntity be, @RequestBody long amount) {
    	return einkaufService.createCompareObjectForPersonOrder(be, amount);
    }
    
    @GetMapping(value = "/einkaufe/create/EinkaufBestellungVergleich")
    public BestandBuyEntity getBBEFromData(@RequestBody BestandEntity be, @RequestBody long amount) {
    	return einkaufService.createBestandBuyEntityForPersonOrder(be, amount);
    } 
    
    @PostMapping(value = "/einkauf/{id}")
    public EinkaufEntity executeShopping(@PathVariable String id, @RequestBody List<BestandBuyEntity> bbe, @RequestBody List<EinkaufBestellungVergleich> ebv) {
    	return einkaufService.einkaufDurchführen(id, ebv, bbe);
    }
    
}
