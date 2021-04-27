package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProduktToRepresentationMapperTest {
    @Mock
    private LagerbestandToRepresentationMapper lagerbestandToRepresentationMapper;
    @InjectMocks
    private ProduktToRepresentationMapper toBeTested;

    @Test
    @DisplayName("ProduktToRepresentationMapper Works Test")
    void applySuccessfully() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "einheit");
        Lagerbestand lagerbestand = new Lagerbestand(einheit, 0.0000001, 199999.1);
        Produkt given = new Produkt(TestUtils.PRODUKT_TEST_ID
                , "test"
                , new Kategorie(TestUtils.KATEGORIE_TEST_ID, "kategorie", TestUtils.BASICICON, List.of())
                , lagerbestand);

        when(lagerbestandToRepresentationMapper.apply(lagerbestand))
                .thenReturn(new LagerbestandRepresentation(
                        new EinheitRepresentation(einheit.getId(), einheit.getName())
                        , lagerbestand.getIstLagerbestand()
                        , lagerbestand.getSollLagerbestand()
                ));
        ProduktRepresentation when = toBeTested.apply(given);

        Assertions.assertEquals(TestUtils.PRODUKT_TEST_ID, when.getId());
        Assertions.assertEquals("test", when.getName());
        Assertions.assertEquals(TestUtils.KATEGORIE_TEST_ID, when.getKategorie());
        Assertions.assertEquals(given.getLagerbestand().getEinheit().getId()
                , when.getLagerbestandRepresentation().getEinheitRepresentation().getId());
    }
}