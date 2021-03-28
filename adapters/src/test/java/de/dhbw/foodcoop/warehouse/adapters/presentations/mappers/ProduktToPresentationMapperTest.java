package de.dhbw.foodcoop.warehouse.adapters.presentations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProduktToPresentationMapperTest {
    private  final ProduktToPresentationMapper mapper = new ProduktToPresentationMapper();

    @Test
    void applySuccessfully() {
        Produkt given = new Produkt();
        ProduktPresentation when = mapper.apply(given);
        Assertions.assertNotNull(when.getId());
        Assertions.assertEquals("undefined", when.getName());
        Assertions.assertEquals(given.getKategorie().getId(), when.getKategorie());
        Assertions.assertEquals(given.getLagerbestand(), when.getLagerbestand());
    }
}