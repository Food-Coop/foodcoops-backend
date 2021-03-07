package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.LagerbestandToLagerbestandResourceMapper;
import org.springframework.web.bind.annotation.RestController;
import de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht.LagerbestandUIService;

@RestController
public class LagerbestandController {
private LagerbestandUIService ansicht;
private LagerbestandToLagerbestandResourceMapper mapper;

}