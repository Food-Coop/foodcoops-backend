package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

class KategorieResourceToKategorieMapperTest {
    private final KategorieResourceToKategorieMapper toBeTested = new KategorieResourceToKategorieMapper();

    @Test
    void applySuccessfullyNoId() {
        KategorieResource given = new KategorieResource(null, "test", "123abd", new ArrayList<>());
        Kategorie when = toBeTested.apply(given);
        Assertions.assertNotNull(when.getId());
        Assertions.assertEquals("test", when.getName());
        Assertions.assertEquals("123abd", when.getIcon());
    }

    @Test
    void applySuccessfullyWithId() {
        String uuid = UUID.randomUUID().toString();
        KategorieResource given = new KategorieResource(uuid, "test", "123abd", new ArrayList<>());
        Kategorie when = toBeTested.apply(given);
        Assertions.assertEquals(uuid, when.getId());
        Assertions.assertEquals("test", when.getName());
        Assertions.assertEquals("123abd", when.getIcon());
    }
}