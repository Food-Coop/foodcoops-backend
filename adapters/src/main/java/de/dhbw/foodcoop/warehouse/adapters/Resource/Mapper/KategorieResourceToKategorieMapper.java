package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class KategorieResourceToKategorieMapper implements Function<KategorieResource, Kategorie> {
    public KategorieResourceToKategorieMapper() {
    }

    @Override
    public Kategorie apply(KategorieResource kategorieResource) {
        if (kategorieResource.getId().isEmpty()) {
            return new Kategorie(kategorieResource.getName()
                    , kategorieResource.getIcon()
                    , null);
        }
        return new Kategorie(kategorieResource.getId()
                , kategorieResource.getName()
                , kategorieResource.getIcon()
                , null);
    }

}
