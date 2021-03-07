package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.LagerbestandToLagerbestandResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht.LagerbestandUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class LagerbestandController {
    private final LagerbestandUIService ansicht;
    private final LagerbestandToLagerbestandResourceMapper mapper;

    @Autowired
    public LagerbestandController(LagerbestandUIService ansicht, LagerbestandToLagerbestandResourceMapper mapper) {
        this.ansicht = ansicht;
        this.mapper = mapper;
    }
}