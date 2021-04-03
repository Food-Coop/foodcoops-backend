package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataEinheitRepository extends JpaRepository<Einheit, String> {
}
