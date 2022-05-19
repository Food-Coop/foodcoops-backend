package de.dhbw.foodcoop.warehouse.application.deadline;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.repositories.DeadlineRepository;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeadlineServiceTest {
    @InjectMocks
    DeadlineService toBeTested;
    @Mock
    DeadlineRepository mockRepository;

    @Test
    void testAll() {
        Deadline d0 = new Deadline("1234", "Montag", time, ts);
        Deadline d1 = new Deadline("2345", "Dienstag", time, ts);
        Deadline d2 = new Deadline("3456", "Nittwoch", time, ts);

        when(mockRepository.alle()).thenReturn(Arrays.asList(d0, d1, d2));
        List<Deadline> whenReturn = toBeTested.all();

        Assertions.assertEquals("1234", whenReturn.get(0).getId());
        Assertions.assertEquals("2345", whenReturn.get(1).getId());
        Assertions.assertEquals("3456", whenReturn.get(2).getId());
    }

    // @Test
    // void testDeleteById() {
    //     Deadline deadline = new Deadline("1234", "Montag", time, ts);

    //     when(mockRepository.findeMitId(deadline.getId())).thenReturn(Optional.empty());
    //     toBeTested.deleteById(deadline.getId());

    //     verify(mockRepository, Mockito.never()).deleteById(any());
    // }

    @Test
    void testFindById() {
        Deadline deadline = new Deadline("1234", "Montag", time, ts);

        when(mockRepository.findeMitId(deadline.getId())).thenReturn(Optional.of(deadline));
        Optional<Deadline> whenReturn = toBeTested.findById(deadline.getId());

        Assertions.assertEquals(deadline.getId(), whenReturn.get().getId());
    }

    @Test
    void testSave() {
        Deadline newDeadline = new Deadline("1234", "Montag", time, ts);
        
        lenient().when(mockRepository.alle()).thenReturn(List.of(new Deadline("1234", "Montag", time, ts)));
        lenient().when(mockRepository.speichern(newDeadline)).thenReturn(newDeadline);
        Deadline returnVal = toBeTested.save(newDeadline);

        Assertions.assertEquals("1234", returnVal.getId());
        Assertions.assertEquals("Montag", returnVal.getWeekday());
        Assertions.assertEquals(time, returnVal.getTime());
        Assertions.assertEquals(ts, returnVal.getDatum());
    }

    long milli = 123456789999l;
    Time time = new Time(milli);
    Timestamp ts = Timestamp.from(Instant.now());
}
