package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.ConfigurationRepository;
import de.dhbw.foodcoop.warehouse.domain.utils.ConstantsUtils;

@Repository
public class ConfigurationRepositoryBridge implements ConfigurationRepository {
	
	
	@Autowired
	private SpringDataConfigurationRepository configuration;
	
	@Override
	public Optional<ConfigurationEntity> getConfiguration() {
		return configuration.findById(ConstantsUtils.CONFIGURATION_ID);
	}
	
	@Override
	public ConfigurationEntity updateConfiguration(ConfigurationEntity ce) {
		return configuration.save(ce);
	}

}
