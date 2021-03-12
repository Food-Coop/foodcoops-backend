package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper.ProduktToProduktResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerService.LagerResourceService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lager")
public class LagerController {
    private final LagerResourceService LagerResourceService;
    private final ProduktToProduktResourceMapper mapper;

    @Autowired
    public LagerController(LagerResourceService LagerResourceService, ProduktToProduktResourceMapper mapper) {
        this.LagerResourceService = LagerResourceService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ProduktResource> getAllProdukts() {
        List<Produkt> produkts = LagerResourceService.getAllProdukts();
        return produkts.stream().map(mapper).collect(Collectors.toList());
    }
}