package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktToProduktResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht.ProduktUIService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.GET)
    public List<ProduktResource> getAllProdukts() {
        List<Produkt> produkts = produktUIService.getAllProdukts();
        return produkts.stream().map(mapper).collect(Collectors.toList());
    }
}