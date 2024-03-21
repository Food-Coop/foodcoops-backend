package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.einkauf.EinkaufService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.EinkaufRequest;

@RestController
public class EinkaufController {
	
    private final EinkaufService einkaufService;
    

    @Autowired
    public EinkaufController(EinkaufService einkaufService) {
    	this.einkaufService = einkaufService;
    }
    


    
    @GetMapping(value = "/example/empty")
    public List<BestandBuyEntity> testEBV3() {
    	
    	List<BestandBuyEntity> test = new ArrayList<>();
    	return test;
    }
    
    @GetMapping(value = "/einkaufe")
    public List<EinkaufEntity> einkäufe() {
    	
    	return einkaufService.all();
    }
    

    
    @GetMapping(value = "/einkaufe/create/Bestand")
    public BestandBuyEntity getBBEFromData(@RequestBody BestandEntity be, @RequestBody long amount) {
    	return einkaufService.createBestandBuyEntityForPersonOrder(be, amount);
    } 
    
    @PostMapping(value = "/einkauf")
    public EinkaufEntity executeShopping(@RequestBody EinkaufRequest er) {
    	return einkaufService.einkaufDurchführen(er.getPersonId(), er.getBestellungen(),er.getBuy());
    }
    
}
