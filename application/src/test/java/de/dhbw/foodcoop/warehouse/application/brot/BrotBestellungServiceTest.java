package de.dhbw.foodcoop.warehouse.application.brot;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
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

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;

@ExtendWith(MockitoExtension.class)
public class BrotBestellungServiceTest {
    @InjectMocks
    BrotBestellungService toBeTested;
    @Mock
    BrotBestellungRepository mockRepository;

    @Test
    void testAll() {
        BrotBestellung f0 = new BrotBestellung("1234", "Peter_Meier", bb, 4, ts);
        BrotBestellung f1 = new BrotBestellung("2345", "Peter_Müller", bb, 2, ts);
        BrotBestellung f2 = new BrotBestellung("3456", "Peter_Maier", bb, 3, ts);

        when(mockRepository.alle()).thenReturn(Arrays.asList(f0, f1, f2));
        List<BrotBestellung> whenReturn = toBeTested.all();

        Assertions.assertEquals("1234", whenReturn.get(0).getId());
        Assertions.assertEquals("2345", whenReturn.get(1).getId());
        Assertions.assertEquals("3456", whenReturn.get(2).getId());
    }

    @Test
    void testFindById() {
        BrotBestellung frischBestellung = new BrotBestellung("1234", "Peter_Meier", bb, 4, ts);
        
        when(mockRepository.findeMitId(frischBestellung.getId())).thenReturn(Optional.of(frischBestellung));
        Optional<BrotBestellung> whenReturn = toBeTested.findById(frischBestellung.getId());

        Assertions.assertEquals(frischBestellung.getId(), whenReturn.get().getId());
    
    }

    @Test
    void testSave() {
        BrotBestellung newBrotBestellung = new BrotBestellung("1234", "Peter_Meier", bb, 4, ts);
        
        lenient().when(mockRepository.alle()).thenReturn(List.of(new BrotBestellung("1234", "Peter_Meier", bb, 4, ts)));
        lenient().when(mockRepository.speichern(newBrotBestellung)).thenReturn(newBrotBestellung);
        BrotBestellung returnVal = toBeTested.save(newBrotBestellung);

        Assertions.assertEquals("1234", returnVal.getId());
        Assertions.assertEquals("Peter_Meier", returnVal.getPersonId());
        Assertions.assertEquals(bb, returnVal.getBrotBestand());
        Assertions.assertEquals(4, returnVal.getBestellmenge());
        Assertions.assertEquals(ts, returnVal.getDatum());
    }

    BrotBestand bb = new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5);
    LocalDateTime ts= LocalDateTime.now();
}
