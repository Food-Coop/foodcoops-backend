//package de.dhbw.foodcoop.warehouse.application.lager;
//
//import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
//import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
//import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieInUseException;
//import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
//import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
//import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
//import de.dhbw.foodcoop.warehouse.domain.values.Icon;
//import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class KategorieServiceTest {
//    @Mock
//    private KategorieRepository mockRepository;
//    @InjectMocks
//    private KategorieService toBeTested;
//
//    @Test
//    @DisplayName("Kategorie Sort Test")
//    public void getAllKategoriesSorted() {
//        Kategorie a = new Kategorie("A", new Icon(TestUtils.BASICICON), new ArrayList<>());
//        Kategorie b = new Kategorie("B", new Icon(TestUtils.BASICICON), new ArrayList<>());
//        Kategorie uea = new Kategorie("Üa", new Icon(TestUtils.BASICICON), new ArrayList<>());
//
//        when(mockRepository.alle()).thenReturn(Arrays.asList(uea, b, a));
//        List<Kategorie> whenReturn = toBeTested.all();
//
//        Assertions.assertEquals("A", whenReturn.get(0).getName());
//        Assertions.assertEquals("B", whenReturn.get(1).getName());
//        Assertions.assertEquals("Üa", whenReturn.get(2).getName());
//    }
//
//    @Test
//    @DisplayName("Kategorie Deletes Successfully")
//    public void deleteSuccessfully() {
//        Kategorie obst = getKategorie(List.of());
//
//        when(mockRepository.findeMitId(obst.getId())).thenReturn(Optional.of(obst));
//        doNothing().when(mockRepository).deleteById(obst.getId());
//
//        toBeTested.deleteById(obst.getId());
//
//        Assertions.assertDoesNotThrow(() -> new KategorieInUseException(obst.getId()));
//        verify(mockRepository, times(1)).deleteById(obst.getId());
//    }
//
//    @Test
//    @DisplayName("Kategorie Delete Throws Exception When Kategorien In Use")
//    public void deleteThrowsExcpetion() {
//        Produkt apfel = getProdukt();
//        Kategorie obst = getKategorie(List.of(apfel));
//        apfel.setKategorie(obst);
//
//        when(mockRepository.findeMitId(obst.getId())).thenReturn(Optional.of(obst));
//
//        Assertions.assertThrows(KategorieInUseException.class, () -> toBeTested.deleteById(obst.getId()));
//        verify(mockRepository, times(0)).deleteById(obst.getId());
//    }
//
//    private Kategorie getKategorie(List<Produkt> produkts) {
//        return new Kategorie(TestUtils.KATEGORIE_TEST_ID
//                , "Obst", new Icon(TestUtils.BASICICON)
//                , produkts);
//    }
//
//    private Produkt getProdukt() {
//        return new Produkt(TestUtils.PRODUKT_TEST_ID
//                , "Apfel"
//                , null
//                , new Lagerbestand(new Einheit("test"), 0.0, 4.0));
//    }
//}