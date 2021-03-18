package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.LagerbestandResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktResource;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProduktToProduktResourceMapperTest {
    @Mock
    private LagerbestandToLagerbestandResourceMapper mockMapper;

    @InjectMocks
    private ProduktToProduktResourceMapper toBeTested;

    @Test
    public void applySuccessfully() {
        Kategorie givenKategorie = new Kategorie();
        Lagerbestand givenLagerbestand = new Lagerbestand();
        Produkt given = new Produkt("uuid", "name", givenKategorie, givenLagerbestand);

        when(mockMapper.apply(givenLagerbestand)).thenReturn(new LagerbestandResource());
        ProduktResource when = toBeTested.apply(given);

        Assertions.assertEquals("uuid", when.getId());
        Assertions.assertEquals("name", when.getName());
        Assertions.assertEquals(givenKategorie.getId(), when.getKategorieId());
        Mockito.verify(mockMapper).apply(givenLagerbestand);
    }
}