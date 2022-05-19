package de.dhbw.foodcoop.warehouse.application.brot;

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

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestandRepository;

@ExtendWith(MockitoExtension.class)
public class BrotBestandServiceTest {

    @InjectMocks
    BrotBestandService toBeTested;
    @Mock
    BrotBestandRepository mockRepository;

    @Test
    void testAll() {
        BrotBestand b0 = new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5);
        BrotBestand b1 = new BrotBestand("2345", "Laugenbrot", true, 750, (float) 2.0);
        BrotBestand b2 = new BrotBestand("3456", "Durlacherbrot", false, 1000, (float) 2.5);

        when(mockRepository.alle()).thenReturn(Arrays.asList(b0, b1, b2));
        List<BrotBestand> whenReturn = toBeTested.all();

        Assertions.assertEquals("1234", whenReturn.get(0).getId());
        Assertions.assertEquals("2345", whenReturn.get(1).getId());
        Assertions.assertEquals("3456", whenReturn.get(2).getId());
    }

    @Test
    void testDeleteById() {
        BrotBestand frischBestand = new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5);

        when(mockRepository.findeMitId(frischBestand.getId())).thenReturn(Optional.empty());
        toBeTested.deleteById(frischBestand.getId());

        verify(mockRepository, Mockito.never()).deleteById(any());
    }

    @Test
    void testFindById() {
        BrotBestand frischBestand = new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5);

        when(mockRepository.findeMitId(frischBestand.getId())).thenReturn(Optional.of(frischBestand));
        Optional<BrotBestand> whenReturn = toBeTested.findById(frischBestand.getId());

        Assertions.assertEquals(frischBestand.getId(), whenReturn.get().getId());
    }

    @Test
    void testSave() {
        BrotBestand newBrotBestand = new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5);
        
        lenient().when(mockRepository.alle()).thenReturn(List.of(new BrotBestand("1234", "Bauernbrot", true, 500, (float) 1.5)));
        lenient().when(mockRepository.speichern(newBrotBestand)).thenReturn(newBrotBestand);
        BrotBestand returnVal = toBeTested.save(newBrotBestand);

        Assertions.assertEquals("1234", returnVal.getId());
        Assertions.assertEquals("Bauernbrot", returnVal.getName());
        Assertions.assertEquals(true, returnVal.getVerfuegbarkeit());
        Assertions.assertEquals(500, returnVal.getGewicht());
        Assertions.assertEquals((float)1.5, returnVal.getPreis());
    }
        
}
