package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

public interface SpringDataEinkaufBestellungVergleichRepository extends JpaRepository<EinkaufBestellungVergleich, String>{

}
