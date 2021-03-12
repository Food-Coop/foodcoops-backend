package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.KategorieRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataKategorieRowRepository extends JpaRepository<KategorieRow, String> {
}
