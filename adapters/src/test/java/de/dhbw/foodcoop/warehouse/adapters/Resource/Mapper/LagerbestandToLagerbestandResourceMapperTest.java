package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.LagerbestandResource;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LagerbestandToLagerbestandResourceMapperTest {
    final LagerbestandToLagerbestandResourceMapper toBeTested = new LagerbestandToLagerbestandResourceMapper();

    @Test
    void applyWithSuccess() {
        Lagerbestand given = new Lagerbestand();

        LagerbestandResource when = toBeTested.apply(given);

        Assertions.assertNotNull(when);
        Assertions.assertEquals(0, when.getIstLagerbestand());
        Assertions.assertEquals(0, when.getSollLagerbestand());
        Assertions.assertEquals("undefined", when.getEinheit());
    }
}