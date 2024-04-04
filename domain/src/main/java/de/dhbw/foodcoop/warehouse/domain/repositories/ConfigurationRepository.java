package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;

public interface ConfigurationRepository {

	Optional<ConfigurationEntity> getConfiguration();
	
	ConfigurationEntity updateConfiguration(ConfigurationEntity e);
	
}
