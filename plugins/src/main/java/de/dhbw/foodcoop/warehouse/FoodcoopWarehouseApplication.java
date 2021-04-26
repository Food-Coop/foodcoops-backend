package de.dhbw.foodcoop.warehouse;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class FoodcoopWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodcoopWarehouseApplication.class, args);
    }

 /*   @Bean
    public CommandLineRunner demoGemuese(KategorieRepository repository) {
        return (args) -> repository.speichern(initKategorieGemuese());
    }

    @Bean
    public CommandLineRunner demoTeigwaren(KategorieRepository repository) {
        return (args) -> repository.speichern(initKategorieTeigwaren());
    }*/

    private Kategorie initKategorieGemuese() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, "kg");
        Lagerbestand lagerbestand1 = new Lagerbestand(einheit, 12.2, 15.0);
        Lagerbestand lagerbestand2 = new Lagerbestand(einheit, 13.1, 18.0);
        Produkt karrotten = new Produkt(TestUtils.PRODUKT_TEST_ID, "Karrotten", null, lagerbestand1);
        Produkt roteBeete = new Produkt("Rote Beete", null, lagerbestand2);
        Kategorie gemuese = new Kategorie(TestUtils.KATEGORIE_TEST_ID
                ,"Gemüse"
                , TestUtils.GEMUESEICON
                , Arrays.asList(karrotten, roteBeete));
        karrotten.setKategorie(gemuese);
        roteBeete.setKategorie(gemuese);
        return gemuese;
    }

    private Kategorie initKategorieTeigwaren() {
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID ,"kg");
        Lagerbestand lagerbestand1 = new Lagerbestand(einheit, 12.1, 15.9);
        Lagerbestand lagerbestand2 = new Lagerbestand(einheit, 13.0, 18.1);
        Produkt weissbrot = new Produkt("Kasten Weißbrot", null, lagerbestand1);
        Produkt roggenbrot = new Produkt("Roggenbrot", null, lagerbestand2);
        Kategorie teigwaren = new Kategorie("Teigwaren"
                , TestUtils.TEIGWARENICON
                , Arrays.asList(weissbrot, roggenbrot));
        weissbrot.setKategorie(teigwaren);
        roggenbrot.setKategorie(teigwaren);
        return teigwaren;
    }
}
