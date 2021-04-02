package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProduktToRepresentationMapperTest {
    private  final ProduktToRepresentationMapper mapper = new ProduktToRepresentationMapper();

    @Test
    @DisplayName("ProduktToRepresentationMapper Works Test")
    void applySuccessfully() {
        Produkt given = new Produkt();
        ProduktRepresentation when = mapper.apply(given);
        Assertions.assertNotNull(when.getId());
        Assertions.assertEquals("undefined", when.getName());
        Assertions.assertEquals(given.getKategorie().getId(), when.getKategorie());
        Assertions.assertEquals(given.getLagerbestand(), when.getLagerbestand());
    }
}