package de.dhbw.foodcoop.warehouse.adapters.presentations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.application.LagerService.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.exceptions.KategorieNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresentationToProduktMapperTest {
    @Mock
    private KategorieService kategorieService;
    @InjectMocks
    private PresentationToProduktMapper mapper;

    @Test
    void applySuccessfully() {
        Kategorie kategorie = new Kategorie();
        ProduktPresentation given = new ProduktPresentation(TestUtils.PRODUKT_TEST_ID, "abc", kategorie.getId(), new Lagerbestand());

        when(kategorieService.findById(kategorie.getId())).thenReturn(Optional.of(kategorie));
        Produkt then = mapper.apply(given);

        Assertions.assertNotNull(then);
        Assertions.assertEquals(TestUtils.PRODUKT_TEST_ID, then.getId());
        Assertions.assertNotNull(then.getKategorie());
        Assertions.assertEquals(TestUtils.KATEGORIE_TEST_ID, then.getKategorie().getId());
        Assertions.assertNotNull(then.getLagerbestand());
        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID, then.getLagerbestand().getEinheit().getId());
    }

    @Test
    void applyWithException() {
        Kategorie kategorie = new Kategorie();
        ProduktPresentation given = new ProduktPresentation(TestUtils.PRODUKT_TEST_ID, "abc", kategorie.getId(), new Lagerbestand());

        when(kategorieService.findById(kategorie.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(KategorieNotFoundException.class,
                () -> { mapper.apply(given); },
                "Could not find Kategorie " + kategorie.getId());
    }
}