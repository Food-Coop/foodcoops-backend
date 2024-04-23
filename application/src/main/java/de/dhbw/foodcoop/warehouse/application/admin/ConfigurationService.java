package de.dhbw.foodcoop.warehouse.application.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.ConfigurationRepository;

@Service
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository config;
	
	
	public Optional<ConfigurationEntity> getConfig() {
		return config.getConfiguration();
	}
	
	public ConfigurationEntity updateConfig(ConfigurationEntity ce) {
		return config.updateConfiguration(ce);
	}
}
