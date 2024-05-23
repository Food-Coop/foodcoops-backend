package de.dhbw.foodcoop.warehouse.application.gebindemanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.invoke.WrongMethodTypeException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

@ExtendWith(MockitoExtension.class)
public class GebindemanagementServiceTest {

    @Mock
    private DeadlineService deadlineService;

    @Mock
    private FrischBestellungService frischBestellungService;

    @InjectMocks
    private GebindemanagementService gebindemanagementService;

    private Kategorie kategorie;
    private FrischBestellung bestellung1;
    private FrischBestellung bestellung2;
    private FrischBestand frischbestand;
    private FrischBestand frischbestand2;

    @BeforeEach
    void setUp() {
        kategorie = new Kategorie("1", "Gemüse", true);

        frischbestand = new FrischBestand();
        frischbestand.setKategorie(kategorie);
        frischbestand.setGebindegroesse(10);
        frischbestand.setId("1");
        frischbestand.setName("TestBestand1");

        frischbestand2 = new FrischBestand();
        frischbestand2.setKategorie(kategorie);
        frischbestand2.setGebindegroesse(10);
        frischbestand2.setId("2");
        frischbestand2.setName("TestBestand2");
        
        bestellung1 = new FrischBestellung();
        bestellung1.setId("1");
        bestellung1.setFrischbestand(frischbestand);
        bestellung1.setDatum(LocalDateTime.now().minusSeconds(10));

        bestellung2 = new FrischBestellung();
        bestellung2.setId("2");
        bestellung2.setFrischbestand(frischbestand2);
        bestellung2.setDatum(LocalDateTime.now().minusSeconds(5));
        when(deadlineService.getByPosition(1)).thenReturn(Optional.of(new Deadline(UUID.randomUUID().toString(), DeadlineService.germanDaysOfWeekReversed.get(LocalDateTime.now().getDayOfWeek()), Time.valueOf(LocalTime.now()), LocalDateTime.now())));
        when(frischBestellungService.findAllOrdersAfterDate(any(LocalDateTime.class)))
            .thenReturn(Arrays.asList(bestellung1, bestellung2));
    }

    @Test
    void testGetDiscrepancyListForFittingMixableCategorie() {
    	bestellung1.setBestellmenge(5);
    	bestellung2.setBestellmenge(3);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyListForMixableCategorie(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Threshold ist 80%, 3 + 5 = 8; passt bei Gebindegröße 10.
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(5, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(0, t.getZuBestellendeGebinde());
        		assertEquals(-3, t.getZuVielzuWenig());
        	}
        });
    }

    @Test
    void testGetDiscrepancyListForNotFittingMixableCategorie() {
    	bestellung1.setBestellmenge(5);
    	bestellung2.setBestellmenge(2);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyListForMixableCategorie(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Threshold ist 80%, 2 + 5 = 7; passt nicht bei Gebindegröße 10.
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(0, t.getZuBestellendeGebinde());
        		assertEquals(-5, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(0, t.getZuBestellendeGebinde());
        		assertEquals(-2, t.getZuVielzuWenig());
        	}
        });
    }
    
    @Test
    void testGetDiscrepancyListForBothFittingMixableCategorie() {
    	bestellung1.setBestellmenge(12);
    	bestellung2.setBestellmenge(16);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyListForMixableCategorie(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // Threshold ist 80%, 12 + 16 = 28; passt bei Gebindegröße 10 3x.
        // -2 + 4 = 2 insgesamt sind zwei zu viel.
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(-2, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(2, t.getZuBestellendeGebinde());
        		assertEquals(4, t.getZuVielzuWenig());
        	}
        });
    }
    
    @Test
    void testGetDiscrepancyListForSizeOneMixableCategorie() {
    	
    	frischbestand.setGebindegroesse(1);
    	
    	bestellung1.setBestellmenge(12);
    	bestellung2.setBestellmenge(16);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyListForMixableCategorie(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // Threshold ist 80%, 12 + 16 = 28; passt bei Gebindegröße 10 3x.
        // -2 + 4 = 2 insgesamt sind zwei zu viel.
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(12, t.getZuBestellendeGebinde());
        		assertEquals(0, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(-6, t.getZuVielzuWenig());
        	}
        });
    }
    
    
    @Test
    void testGetDiscrepancyListForMixableCategorie_WrongMethodTypeException() {
        kategorie.setMixable(false);

        assertThrows(WrongMethodTypeException.class, () -> {
            gebindemanagementService.getDiscrepancyListForMixableCategorie(kategorie, 80);
        });
    }
    
    @Test
    void testGetDiscrepancyListForNonMixableCategorie_WrongMethodTypeException() {
        kategorie.setMixable(true);

        assertThrows(WrongMethodTypeException.class, () -> {
            gebindemanagementService.getDiscrepancyForNotMixableOrder(kategorie, 80);
        });
    }
    
    @Test
    void testGetDiscrepancyListForFittingNotMixableCategorie() {
    	frischbestand.getKategorie().setMixable(false);
    	bestellung1.setBestellmenge(8);
    	bestellung2.setBestellmenge(11);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyForNotMixableOrder(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(2, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(-1, t.getZuVielzuWenig());
        	}
        });
    }

    @Test
    void testGetDiscrepancyListForNotFittingNotMixableCategorie() {
    	frischbestand.getKategorie().setMixable(false);
    	bestellung1.setBestellmenge(5);
    	bestellung2.setBestellmenge(17);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyForNotMixableOrder(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        /**
         *  	Achtung: Bei not mixable orders ergibt zu viel zu wenig 0 wenn nichts zustande kommt.
         *  			 Dies hat den Hintergrund zwecks einer Berechnung im Frontend
         *  			 Kommt jedoch mindestens ein zu bestellendes Gebinde zusammen, wird ein Wert eingetragen. Siehe Testfall bei dem Bestand ID 2 ist.
         */
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(0, t.getZuBestellendeGebinde());
        		assertEquals(0, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(-7, t.getZuVielzuWenig());
        	}
        });
    }
    
    @Test
    void testGetDiscrepancyListForBothFittingNotMixableCategorie() {
    	frischbestand.getKategorie().setMixable(false);
    	bestellung1.setBestellmenge(30);
    	bestellung2.setBestellmenge(21);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyForNotMixableOrder(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(3, t.getZuBestellendeGebinde());
        		assertEquals(0, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(2, t.getZuBestellendeGebinde());
        		assertEquals(1, t.getZuVielzuWenig());
        	}
        });
    }
    
    @Test
    void testGetDiscrepancyListForSizeOneNotMixableCategorie() {
    	frischbestand.getKategorie().setMixable(false);
    	frischbestand.setGebindegroesse(1);
    	
    	bestellung1.setBestellmenge(12);
    	bestellung2.setBestellmenge(16);
        List<DiscrepancyEntity> result = gebindemanagementService.getDiscrepancyForNotMixableOrder(kategorie, 80);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        
        // Threshold ist 80%, 12 + 16 = 28; passt bei Gebindegröße 10 3x.
        // -2 + 4 = 2 insgesamt sind zwei zu viel.
        result.forEach(t -> {
        	if(t.getBestand().getId().equalsIgnoreCase("1")) {
        		assertEquals(12, t.getZuBestellendeGebinde());
        		assertEquals(0, t.getZuVielzuWenig());
        	} else if(t.getBestand().getId().equalsIgnoreCase("2")) {
        		assertEquals(1, t.getZuBestellendeGebinde());
        		assertEquals(-6, t.getZuVielzuWenig());
        	}
        });
    }
}