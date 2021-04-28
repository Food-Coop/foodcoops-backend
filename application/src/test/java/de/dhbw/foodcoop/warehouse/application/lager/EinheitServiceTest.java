package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitInUseException;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinheitRepository;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Icon;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EinheitServiceTest {
    @InjectMocks
    EinheitService toBeTested;
    @Mock
    ProduktService mockProduktService;
    @Mock
    EinheitRepository mockRepository;

    @Test
    void saveNew() {
        Einheit newEinheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "Liter");

        when(mockRepository.alle()).thenReturn(List.of(new Einheit(TestUtils.EINHEIT_TEST_ID_2, "kg")));
        when(mockRepository.speichern(newEinheit)).thenReturn(newEinheit);
        Einheit returnVal = toBeTested.save(newEinheit);

        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID, returnVal.getId());
        Assertions.assertEquals("Liter", newEinheit.getName());
    }

    @Test
    void saveExisting() {
        Einheit oldEinheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "Liter");

        when(mockRepository.alle()).thenReturn(List.of(new Einheit(TestUtils.EINHEIT_TEST_ID_2, "Liter")));
        Einheit returnVal = toBeTested.save(oldEinheit);

        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID_2, returnVal.getId());
        Assertions.assertEquals("Liter", oldEinheit.getName());
    }

    @Test
    void deleteById() {
        Einheit oldEinheint = new Einheit(TestUtils.EINHEIT_TEST_ID, "Gramm");
        Einheit otherEinheit = new Einheit(TestUtils.EINHEIT_TEST_ID_2, "meter");
        Lagerbestand lagerbestand = new Lagerbestand(otherEinheit, 0.8, 1.5);
        Kategorie kategorie = new Kategorie(TestUtils.KATEGORIE_TEST_ID
                , "Fleischwaren"
                , new Icon(TestUtils.TEIGWARENICON),
                List.of());
        Produkt produkt = new Produkt(TestUtils.PRODUKT_TEST_ID
                , "Blutwurst"
                , kategorie
                , lagerbestand);
        when(mockRepository.findeMitId(TestUtils.EINHEIT_TEST_ID))
                .thenReturn(Optional.of(oldEinheint));
        when(mockProduktService.all())
                .thenReturn(List.of(produkt));
        doNothing().when(mockRepository).deleteById(TestUtils.EINHEIT_TEST_ID);

        toBeTested.deleteById(oldEinheint.getId());

        Assertions.assertDoesNotThrow(() -> new EinheitInUseException(TestUtils.EINHEIT_TEST_ID));
        verify(mockRepository, times(1)).deleteById(TestUtils.EINHEIT_TEST_ID);
    }
}