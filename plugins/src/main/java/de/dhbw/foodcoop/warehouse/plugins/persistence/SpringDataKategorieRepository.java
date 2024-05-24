package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataKategorieRepository extends JpaRepository<Kategorie, String> {
	Kategorie findByName(String name);
}
