package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.LagerbestandRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataLagerbestandRowRepository extends JpaRepository<LagerbestandRow, String> {
}
