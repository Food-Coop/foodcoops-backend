package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;

public interface SpringDataTooMuchBuyRepository extends JpaRepository<TooMuchBuyEntity, String> {

}
