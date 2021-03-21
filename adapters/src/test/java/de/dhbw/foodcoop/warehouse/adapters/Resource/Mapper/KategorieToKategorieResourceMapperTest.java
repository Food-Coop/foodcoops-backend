package de.dhbw.foodcoop.warehouse.adapters.Resource.Mapper;

import de.dhbw.foodcoop.warehouse.adapters.Resource.KategorieResource;
import de.dhbw.foodcoop.warehouse.adapters.Resource.ProduktResource;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KategorieToKategorieResourceMapperTest {
    @Mock
    private ProduktToProduktResourceMapper mockMapper;
    @InjectMocks
    private KategorieToKategorieResourceMapper toBeTested;

    @Test
    void applySuccessfully() {
        Produkt givenProdukt = new Produkt();
        Kategorie given = new Kategorie("asdfkwgwiwn3ak"
                , "drinks"
                , "asdiconwe"
                , Collections.singletonList(
                givenProdukt));

        when(mockMapper.apply(givenProdukt)).thenReturn(new ProduktResource());
        KategorieResource when = toBeTested.apply(given);

        Assertions.assertEquals("asdfkwgwiwn3ak", when.getId());
        Assertions.assertEquals("drinks", when.getName());
        Assertions.assertEquals("asdiconwe", when.getIcon());
        Mockito.verify(mockMapper).apply(givenProdukt);
    }
}