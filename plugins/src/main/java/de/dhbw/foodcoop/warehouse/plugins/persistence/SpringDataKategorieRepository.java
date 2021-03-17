package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataKategorieRepository extends JpaRepository<Kategorie, String> {
}
