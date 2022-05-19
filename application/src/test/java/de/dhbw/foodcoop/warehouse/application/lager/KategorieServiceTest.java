package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieInUseException;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KategorieServiceTest {
   @Mock
   private KategorieRepository mockRepository;
   @InjectMocks
   private KategorieService toBeTested;

   @Test
   @DisplayName("Kategorie Sort Test")
   public void getAllKategoriesSorted() {
       Kategorie a = new Kategorie("1234", "Kartoffel");
       Kategorie b = new Kategorie("2345", "Tomate");
       Kategorie uea = new Kategorie("3456", "Salat");

       when(mockRepository.alle()).thenReturn(Arrays.asList(uea, b, a));
       List<Kategorie> whenReturn = toBeTested.all();

       Assertions.assertEquals("Kartoffel", whenReturn.get(0).getName());
       Assertions.assertEquals("Salat", whenReturn.get(1).getName());
       Assertions.assertEquals("Tomate", whenReturn.get(2).getName());
   }

   @Test
   @DisplayName("Kategorie Deletes Successfully")
   public void deleteSuccessfully() {
       Kategorie obst = getKategorie(List.of());

       when(mockRepository.findeMitId(obst.getId())).thenReturn(Optional.of(obst));
       doNothing().when(mockRepository).deleteById(obst.getId());

       toBeTested.deleteById(obst.getId());

       Assertions.assertDoesNotThrow(() -> new KategorieInUseException(obst.getId()));
       verify(mockRepository, times(1)).deleteById(obst.getId());
   }

   private Kategorie getKategorie(List<Produkt> produkts) {
       return new Kategorie("1234", "Apfel");
   }
}