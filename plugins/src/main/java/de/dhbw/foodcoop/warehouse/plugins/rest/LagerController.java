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

    @RequestMapping(name = "/kategorie", method = RequestMethod.PUT)
    public void addKategorie(@RequestParam(value = "kategorieResource"
            , defaultValue =
            "[{\"id\":\"068bb23c-020b-4e53-92a3-fbd65ac9f6e7\"" +
                    ",\"name\":\"Gemüse\"" +
                    ",\"icon\":\"13f5d66a\"" +
                    ", \"null\"}]"
    ) KategorieResource in) {
        Kategorie kategorie = toKategorie.apply(in);
        lagerResourceService.addKategorie(kategorie);
    }
}