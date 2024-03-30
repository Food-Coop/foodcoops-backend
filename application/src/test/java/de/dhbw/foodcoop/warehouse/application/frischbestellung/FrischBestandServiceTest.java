package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestandRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@ExtendWith(MockitoExtension.class)
public class FrischBestandServiceTest {

    @InjectMocks
    FrischBestandService toBeTested;
    @Mock
    FrischBestandRepository mockRepository;

    @Test
    void testAll() {
        FrischBestand f0 = new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5);
        FrischBestand f1 = new FrischBestand("2345", "Eisbergsalat", true, "DE", 10, e1, k1, (float) 2.0);
        FrischBestand f2 = new FrischBestand("3456", "Endiviensalat", false, "DE", 25, e1, k1, (float) 2.5);

        when(mockRepository.alle()).thenReturn(Arrays.asList(f0, f1, f2));
        List<FrischBestand> whenReturn = toBeTested.all();

        Assertions.assertEquals("1234", whenReturn.get(0).getId());
        Assertions.assertEquals("2345", whenReturn.get(1).getId());
        Assertions.assertEquals("3456", whenReturn.get(2).getId());
    }

    @Test
    void testDeleteById() {
        FrischBestand frischBestand = new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5);

        when(mockRepository.findeMitId(frischBestand.getId())).thenReturn(Optional.empty());
        toBeTested.deleteById(frischBestand.getId());

        verify(mockRepository, Mockito.never()).deleteById(any());
    }

    @Test
    void testFindById() {
        FrischBestand frischBestand = new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5);

        when(mockRepository.findeMitId(frischBestand.getId())).thenReturn(Optional.of(frischBestand));
        Optional<FrischBestand> whenReturn = toBeTested.findById(frischBestand.getId());

        Assertions.assertEquals(frischBestand.getId(), whenReturn.get().getId());
    }

    @Test
    void testSave() {
        FrischBestand newFrischBestand = new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5);
        
        lenient().when(mockRepository.alle()).thenReturn(List.of(new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5)));
        lenient().when(mockRepository.speichern(newFrischBestand)).thenReturn(newFrischBestand);
        FrischBestand returnVal = toBeTested.save(newFrischBestand);

        Assertions.assertEquals("1234", returnVal.getId());
        Assertions.assertEquals("Kopfsalat", returnVal.getName());
        Assertions.assertEquals(true, returnVal.getVerfuegbarkeit());
        Assertions.assertEquals("DE", returnVal.getHerkunftsland());
        Assertions.assertEquals(20, returnVal.getGebindegroesse());
        Assertions.assertEquals(new Einheit("111", "Stück"), returnVal.getEinheit());
        Assertions.assertEquals(new Kategorie("222", "Salat", true), returnVal.getKategorie());
        Assertions.assertEquals((float)1.5, returnVal.getPreis());
    }

    Einheit e1 = new Einheit("111", "Stück");
    Kategorie k1 = new Kategorie("222", "Salat", true);
        
}
