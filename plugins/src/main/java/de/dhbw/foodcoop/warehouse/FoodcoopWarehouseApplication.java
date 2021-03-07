package de.dhbw.foodcoop.warehouse;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.LagerbestandRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodcoopWarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodcoopWarehouseApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(LagerbestandRepository repository) {
        return (args) -> repository.saveLagerbestand(new Lagerbestand(new Produkt("Karotte", new Kategorie("Gemüse")
        ), new Menge(new Einheit("kg"), 10), new Menge(new Einheit("kg"), 10)));
    }
}
