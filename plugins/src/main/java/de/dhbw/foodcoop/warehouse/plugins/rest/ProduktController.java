package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktToProduktResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht.ProduktUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProduktController {
    private final ProduktUIService produktUIService;
    private final ProduktToProduktResourceMapper mapper;

    @Autowired
    public ProduktController(ProduktUIService produktUIService, ProduktToProduktResourceMapper mapper) {
        this.produktUIService = produktUIService;
        this.mapper = mapper;
    }
}