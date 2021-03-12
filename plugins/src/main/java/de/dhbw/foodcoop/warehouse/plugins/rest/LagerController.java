package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper.KategorieToKategorieResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerService.LagerResourceService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
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
    private final KategorieToKategorieResourceMapper mapper;

    @Autowired
    public LagerController(LagerResourceService LagerResourceService, KategorieToKategorieResourceMapper mapper) {
        this.LagerResourceService = LagerResourceService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<KategorieResource> getAllProdukts() {
        List<Kategorie> kategories = LagerResourceService.getAllKategories();
        return kategories.stream().map(mapper).collect(Collectors.toList());
    }
}