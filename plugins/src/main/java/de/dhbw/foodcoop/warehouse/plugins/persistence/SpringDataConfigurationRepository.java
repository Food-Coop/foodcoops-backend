package de.dhbw.foodcoop.warehouse.plugins.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;

public interface SpringDataConfigurationRepository extends JpaRepository<ConfigurationEntity, String> {

}
