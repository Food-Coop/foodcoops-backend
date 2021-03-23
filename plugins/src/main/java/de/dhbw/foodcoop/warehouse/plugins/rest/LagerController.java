package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper.KategorieResourceToKategorieMapper;
import de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper.KategorieToKategorieResourceMapper;
import de.dhbw.foodcoop.warehouse.application.LagerService.LagerResourceService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lager")
public class LagerController {
    private final LagerResourceService lagerResourceService;
    private final KategorieToKategorieResourceMapper toResource;
    private final KategorieResourceToKategorieMapper toKategorie;

    @Autowired
    public LagerController(LagerResourceService LagerResourceService, KategorieToKategorieResourceMapper toResource, KategorieResourceToKategorieMapper toKategorie) {
        this.lagerResourceService = LagerResourceService;
        this.toResource = toResource;
        this.toKategorie = toKategorie;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<KategorieResource> getLager() {
        List<Kategorie> kategories = lagerResourceService.getAllKategories();
        return kategories.stream().map(toResource).collect(Collectors.toList());
    }

    @RequestMapping(name = "/kategorie", method = RequestMethod.POST)
    public KategorieResource addKategorie(@RequestParam(value = "kategorieResource"
    ) KategorieResource in) {
        Kategorie kategorieIn = toKategorie.apply(in);
        Kategorie kategorieOut = lagerResourceService.addKategorie(kategorieIn);
        return toResource.apply(kategorieOut);
    }
}