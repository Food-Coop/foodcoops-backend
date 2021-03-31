package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
public class KategorieServiceTest {
    @Mock
    private KategorieRepository mockRepository;
    @InjectMocks
    private KategorieService toBeTested;

    @Test
    @DisplayName("Kategorie Sort Test")
    public void getAllKategoriesSorted() {
        Kategorie a = new Kategorie("A", TestUtils.BASICICON, new ArrayList<>());
        Kategorie b = new Kategorie("B", TestUtils.BASICICON, new ArrayList<>());
        Kategorie uea = new Kategorie("Üa", TestUtils.BASICICON, new ArrayList<>());

        when(mockRepository.alle()).thenReturn(Arrays.asList(uea, b, a));
        List<Kategorie> whenReturn = toBeTested.all();

        Assertions.assertEquals("A", whenReturn.get(0).getName());
        Assertions.assertEquals("B", whenReturn.get(1).getName());
        Assertions.assertEquals("Üa", whenReturn.get(2).getName());
    }
}