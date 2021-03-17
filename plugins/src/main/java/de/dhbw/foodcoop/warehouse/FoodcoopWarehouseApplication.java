package de.dhbw.foodcoop.warehouse;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
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

    @Bean
    public CommandLineRunner demo(KategorieRepository repository) {
        return (args) -> repository.speichern(initKategorieGemuese());
    }

    private Kategorie initKategorieGemuese() {
        Einheit einheit = new Einheit("kg");
        Lagerbestand lagerbestand1 = new Lagerbestand(einheit, 12, 15);
        Lagerbestand lagerbestand2 = new Lagerbestand(einheit, 13, 18);
        Produkt karrotten = new Produkt("Karrotten", null, lagerbestand1);
        Produkt roteBeete = new Produkt("Rote Beete", null, lagerbestand2);
        Kategorie gemuese = new Kategorie("Gemüse", "13f5d66a", Arrays.asList(karrotten, roteBeete));
        karrotten.setKategorie(gemuese);
        roteBeete.setKategorie(gemuese);
        return gemuese;
    }
}
