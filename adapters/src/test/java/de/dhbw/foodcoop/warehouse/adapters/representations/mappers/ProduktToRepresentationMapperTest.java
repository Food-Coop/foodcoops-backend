package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProduktToRepresentationMapperTest {
    private  final ProduktToRepresentationMapper mapper = new ProduktToRepresentationMapper();

    @Test
    @DisplayName("ProduktToRepresentationMapper Works Test")
    void applySuccessfully() {
        Produkt given = new Produkt(TestUtils.PRODUKT_TEST_ID
        , "test"
        , new Kategorie(TestUtils.KATEGORIE_TEST_ID, "kategorie", TestUtils.BASICICON, List.of())
        , new Lagerbestand(new Einheit("test"), 1.7, 2.2));
        ProduktRepresentation when = mapper.apply(given);
        Assertions.assertEquals(TestUtils.PRODUKT_TEST_ID, when.getId());
        Assertions.assertEquals("test", when.getName());
        Assertions.assertEquals(TestUtils.KATEGORIE_TEST_ID, when.getKategorie());
        Assertions.assertEquals(given.getLagerbestand(), when.getLagerbestand());
    }
}