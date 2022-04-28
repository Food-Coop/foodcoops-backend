//package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;
//
//import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
//import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
//import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
//import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
//import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
//import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
//import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
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
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class RepresentationToProduktMapperTest {
//    @Mock
//    private KategorieService kategorieService;
//    @Mock
//    private RepresentationToLagerbestandMapper toLagerbestandMapper;
//    @InjectMocks
//    private RepresentationToProduktMapper mapper;
//
//    @Test
//    @DisplayName("RepresentationToProduktMapper Works Test")
//    void applySuccessfully() {
//        Kategorie kategorie = getKategorie();
//        ProduktRepresentation given = new ProduktRepresentation(
//                TestUtils.PRODUKT_TEST_ID
//                , "abc", kategorie.getId()
//                , getLagerbestandRepresentation());
//
//        when(kategorieService.findById(kategorie.getId())).thenReturn(Optional.of(kategorie));
//        when(toLagerbestandMapper.apply(given.getLagerbestand()))
//                .thenReturn(getLagerbestand());
//        Produkt then = mapper.apply(given);
//
//        Assertions.assertNotNull(then);
//        Assertions.assertEquals(TestUtils.PRODUKT_TEST_ID, then.getId());
//        Assertions.assertNotNull(then.getKategorie());
//        Assertions.assertEquals(TestUtils.KATEGORIE_TEST_ID, then.getKategorie().getId());
//        Assertions.assertNotNull(then.getLagerbestand());
//        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID, then.getLagerbestand().getEinheit().getId());
//    }
//
//    @Test
//    @DisplayName("RepresentationToProduktMapper Throws Exception Test")
//    void applyWithException() {
//        Kategorie kategorie = getKategorie();
//        ProduktRepresentation given = new ProduktRepresentation(
//                TestUtils.PRODUKT_TEST_ID
//                , "abc", kategorie.getId()
//                , getLagerbestandRepresentation());
//
//        when(kategorieService.findById(kategorie.getId())).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(KategorieNotFoundException.class,
//                () -> mapper.apply(given),
//                "Could not find Kategorie " + kategorie.getId());
//    }
//
//    @Test
//    @DisplayName(("Old Produkt and New Produktrepresentation With new Kategorie update Produkt"))
//    public void updateKategorieSuccessfully() {
//        Kategorie oldKategorie = new Kategorie(
//                TestUtils.KATEGORIE_TEST_ID
//                , "Obst", new Icon(TestUtils.BASICICON)
//                , List.of());
//        Kategorie newKategorie = new Kategorie(
//                TestUtils.KATEGORIE_TEST_ID_2
//                , "Früchte", new Icon(TestUtils.BASICICON)
//                , List.of());
//        Lagerbestand lagerbestand = getLagerbestand();
//        Produkt oldProdukt = new Produkt(TestUtils.PRODUKT_TEST_ID
//                , "Apfel"
//                , oldKategorie
//                , lagerbestand);
//        ProduktRepresentation newProduktRepresentation = new ProduktRepresentation(
//                null, "undefined", newKategorie.getId(), null);
//
//        when(kategorieService.findById(newKategorie.getId())).thenReturn(Optional.of(newKategorie));
//        Produkt mapped = mapper.update(oldProdukt, newProduktRepresentation);
//
//        Assertions.assertEquals(oldProdukt.getId(), mapped.getId());
//        Assertions.assertEquals(oldProdukt.getName(), mapped.getName());
//        Assertions.assertEquals(newKategorie, mapped.getKategorie());
//        Assertions.assertEquals(oldProdukt.getLagerbestand(), mapped.getLagerbestand());
//    }
//
//    @Test
//    @DisplayName(("Old Produkt and new Produktrepresentation With new name update Produkt"))
//    public void updateNameSucessfully() {
//        Kategorie kategorie = getKategorie();
//        Lagerbestand lagerbestand = getLagerbestand();
//        Produkt oldProdukt = new Produkt(TestUtils.PRODUKT_TEST_ID
//                , "Apfel"
//                , kategorie
//                , lagerbestand);
//        ProduktRepresentation newProduktRepresentation = new ProduktRepresentation(
//                null, "Birne", "undefined", null);
//
//        Produkt mapped = mapper.update(oldProdukt, newProduktRepresentation);
//
//        Assertions.assertEquals(oldProdukt.getId(), mapped.getId());
//        Assertions.assertEquals(newProduktRepresentation.getName(), mapped.getName());
//        Assertions.assertEquals(oldProdukt.getKategorie(), mapped.getKategorie());
//        Assertions.assertEquals(oldProdukt.getLagerbestand(), mapped.getLagerbestand());
//    }
//
//    @Test
//    @DisplayName(("Old Produkt and new Produktrepresentation With new id Does Not update Produkt"))
//    public void updateIdFailsQuietly() {
//        Kategorie kategorie = getKategorie();
//        Lagerbestand lagerbestand = getLagerbestand();
//        Produkt oldProdukt = new Produkt(TestUtils.PRODUKT_TEST_ID
//                , "Apfel"
//                , kategorie
//                , lagerbestand);
//        ProduktRepresentation newProduktRepresentation = new ProduktRepresentation(
//                TestUtils.EINHEIT_TEST_ID, "undefined", "undefined", null);
//
//        Produkt mapped = mapper.update(oldProdukt, newProduktRepresentation);
//
//        Assertions.assertEquals(oldProdukt.getId(), mapped.getId());
//        Assertions.assertEquals(oldProdukt.getName(), mapped.getName());
//        Assertions.assertEquals(oldProdukt.getKategorie(), mapped.getKategorie());
//        Assertions.assertEquals(oldProdukt.getLagerbestand(), mapped.getLagerbestand());
//    }
//
//    @Test
//    @DisplayName(("Old Produkt and new Produktrepresentation With new id Does Not update Produkt"))
//    public void updateLagerbestandSuccessfully() {
//        Kategorie kategorie = getKategorie();
//        Lagerbestand oldLagerbestand = new Lagerbestand(getEinheit(), 1.4, 9.0);
//        LagerbestandRepresentation newLagerbestand =
//                new LagerbestandRepresentation(getEinheitRepresentation(), 9.0, 9.0);
//        Produkt oldProdukt = new Produkt(TestUtils.PRODUKT_TEST_ID
//                , "Apfel"
//                , kategorie
//                , oldLagerbestand);
//        ProduktRepresentation newProduktRepresentation = new ProduktRepresentation(
//                TestUtils.EINHEIT_TEST_ID, "undefined", "undefined", newLagerbestand);
//
//
//        when(toLagerbestandMapper.apply(newLagerbestand))
//                .thenReturn(new Lagerbestand(
//                        getEinheit()
//                        , newLagerbestand.getIstLagerbestand()
//                        , newLagerbestand.getSollLagerbestand()));
//        Produkt mapped = mapper.update(oldProdukt, newProduktRepresentation);
//
//        Assertions.assertEquals(oldProdukt.getId(), mapped.getId());
//        Assertions.assertEquals(oldProdukt.getName(), mapped.getName());
//        Assertions.assertEquals(oldProdukt.getKategorie(), mapped.getKategorie());
//        Assertions.assertEquals(newProduktRepresentation.getLagerbestand()
//                        .getEinheit().getId()
//                , mapped.getLagerbestand().getEinheit().getId());
//        Assertions.assertEquals(newProduktRepresentation.getLagerbestand().getIstLagerbestand()
//                , mapped.getLagerbestand().getIstLagerbestand());
//        Assertions.assertEquals(newProduktRepresentation.getLagerbestand().getSollLagerbestand()
//                , mapped.getLagerbestand().getSollLagerbestand());
//    }
//
//    private Kategorie getKategorie() {
//        return new Kategorie(
//                TestUtils.KATEGORIE_TEST_ID, "kategorie", new Icon(TestUtils.BASICICON), List.of());
//    }
//
//    private LagerbestandRepresentation getLagerbestandRepresentation() {
//        return new LagerbestandRepresentation(getEinheitRepresentation(), 0.0, 2.9);
//    }
//
//    private Lagerbestand getLagerbestand() {
//        return new Lagerbestand(getEinheit(), 0.0, 2.9);
//    }
//
//    private EinheitRepresentation getEinheitRepresentation() {
//        return new EinheitRepresentation(TestUtils.EINHEIT_TEST_ID, "Knut");
//    }
//
//    private Einheit getEinheit() {
//        return new Einheit(TestUtils.EINHEIT_TEST_ID, "Knut");
//    }
//}