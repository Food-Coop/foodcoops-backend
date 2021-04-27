package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktInUseException;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduktServiceTest {
    @InjectMocks
    ProduktService toBeTested;
    @Mock
    ProduktRepository mockRepository;

    @Test
    public void deleteByIdIsEmpty() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "shilling");
        Lagerbestand lagerbestand = new Lagerbestand(einheit, 0.01, 1.6);
        Kategorie kategorie = new Kategorie(TestUtils.KATEGORIE_TEST_ID
                , "Kategorie"
                , TestUtils.TEIGWARENICON,
                List.of());
        Produkt test = new Produkt(TestUtils.PRODUKT_TEST_ID, kategorie, lagerbestand);

        when(mockRepository.findeMitId(test.getId())).thenReturn(Optional.empty());
        toBeTested.deleteById(test.getId());

        verify(mockRepository, Mockito.never()).deleteById(any());
    }

    @Test
    public void deleteByIdIsInStock() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "shilling");
        Lagerbestand lagerbestand = new Lagerbestand(einheit, 0.1, 1.6);
        Kategorie kategorie = new Kategorie(TestUtils.KATEGORIE_TEST_ID
                , "Kategorie"
                , TestUtils.TEIGWARENICON,
                List.of());
        Produkt produkt = new Produkt(TestUtils.PRODUKT_TEST_ID, kategorie, lagerbestand);

        when(mockRepository.findeMitId(produkt.getId())).thenReturn(Optional.of(produkt));

        Assertions.assertThrows(ProduktInUseException.class
                , () -> toBeTested.deleteById(produkt.getId()));
        verify(mockRepository, Mockito.never()).deleteById(any());
    }

    @Test
    public void deleteByIdSuccess() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "shilling");
        Lagerbestand lagerbestand = new Lagerbestand(einheit, 0.001, 1.6);
        Kategorie kategorie = new Kategorie(TestUtils.KATEGORIE_TEST_ID
                , "Kategorie"
                , TestUtils.TEIGWARENICON,
                List.of());
        Produkt produkt = new Produkt(TestUtils.PRODUKT_TEST_ID, kategorie, lagerbestand);

        when(mockRepository.findeMitId(produkt.getId())).thenReturn(Optional.of(produkt));
        toBeTested.deleteById(produkt.getId());

        verify(mockRepository, Mockito.times(1)).deleteById(any());
    }
}