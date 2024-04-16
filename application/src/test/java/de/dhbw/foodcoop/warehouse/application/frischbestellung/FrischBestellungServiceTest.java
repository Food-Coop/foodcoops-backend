package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@ExtendWith(MockitoExtension.class)
public class FrischBestellungServiceTest {
    @InjectMocks
    FrischBestellungService toBeTested;
    @Mock
    FrischBestellungRepository mockRepository;

    @Test
    void testAll() {
        FrischBestellung f0 = new FrischBestellung("1234", "Peter_Meier", fb, 4, ts, false);
        FrischBestellung f1 = new FrischBestellung("2345", "Peter_Müller", fb, 2, ts, false);
        FrischBestellung f2 = new FrischBestellung("3456", "Peter_Maier", fb, 3, ts, false);

        when(mockRepository.alle()).thenReturn(Arrays.asList(f0, f1, f2));
        List<FrischBestellung> whenReturn = toBeTested.all();

        Assertions.assertEquals("1234", whenReturn.get(0).getId());
        Assertions.assertEquals("2345", whenReturn.get(1).getId());
        Assertions.assertEquals("3456", whenReturn.get(2).getId());
    }

    @Test
    void testFindById() {
        FrischBestellung frischBestellung = new FrischBestellung("1234", "Peter_Meier", fb, 4, ts, false);
        
        when(mockRepository.findeMitId(frischBestellung.getId())).thenReturn(Optional.of(frischBestellung));
        Optional<FrischBestellung> whenReturn = toBeTested.findById(frischBestellung.getId());

        Assertions.assertEquals(frischBestellung.getId(), whenReturn.get().getId());
    
    }

    @Test
    void testSave() {
        FrischBestellung newFrischBestellung = new FrischBestellung("1234", "Peter_Meier", fb, 4, ts, false);
        
        lenient().when(mockRepository.alle()).thenReturn(List.of(new FrischBestellung("1234", "Peter_Meier", fb, 4, ts, false)));
        lenient().when(mockRepository.speichern(newFrischBestellung)).thenReturn(newFrischBestellung);
        FrischBestellung returnVal = toBeTested.save(newFrischBestellung);

        Assertions.assertEquals("1234", returnVal.getId());
        Assertions.assertEquals("Peter_Meier", returnVal.getPersonId());
        Assertions.assertEquals(fb, returnVal.getFrischbestand());
        Assertions.assertEquals(4, returnVal.getBestellmenge());
        Assertions.assertEquals(ts, returnVal.getDatum());
    }

    Einheit e1 = new Einheit("111", "Stück");
    Kategorie k1 = new Kategorie("222", "Salat", true);
    FrischBestand fb = new FrischBestand("1234", "Kopfsalat", true, "DE", 20, e1, k1, (float) 1.5);
    LocalDateTime ts= LocalDateTime.now();  
}
