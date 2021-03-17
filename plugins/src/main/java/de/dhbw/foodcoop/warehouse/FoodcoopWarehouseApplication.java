package de.dhbw.foodcoop.warehouse;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

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
        Lagerbestand lagerbestand1 = new Lagerbestand(new Menge(einheit, 8), new Menge(einheit, 10));
        Lagerbestand lagerbestand2 = new Lagerbestand(new Menge(einheit, 3), new Menge(einheit, 5));
        Produkt karrotten = new Produkt("Karrotten", (Kategorie) null, lagerbestand1);
        Produkt roteBeete = new Produkt("Rote Beete", (Kategorie) null, lagerbestand2);
        Kategorie gemuese = new Kategorie("Gemüse", "13f5d66a", List.of(karrotten, roteBeete));
        karrotten.setKategorie(gemuese);
        roteBeete.setKategorie(gemuese);
        return gemuese;
    }
}
