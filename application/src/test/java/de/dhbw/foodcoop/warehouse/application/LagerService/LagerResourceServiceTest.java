package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LagerResourceServiceTest {
    @Mock
    private KategorieRepository mockRepository;
    @InjectMocks
    private LagerResourceService toBeTested;

    @Test
    public void getAllKategoriesSorted() {
        Kategorie a = new Kategorie("A", "undefined", new ArrayList<>());
        Kategorie b = new Kategorie("B", "undefined", new ArrayList<>());
        Kategorie uea = new Kategorie("Üa", "undefined",new ArrayList<>());

        when(mockRepository.alleKategorienAbrufen()).thenReturn(Arrays.asList(uea, b, a));
        List<Kategorie> whenReturn = toBeTested.getAllKategories();

        Assertions.assertEquals("A", whenReturn.get(0).getName());
        Assertions.assertEquals("B", whenReturn.get(1).getName());
        Assertions.assertEquals("Üa", whenReturn.get(2).getName());
    }
}