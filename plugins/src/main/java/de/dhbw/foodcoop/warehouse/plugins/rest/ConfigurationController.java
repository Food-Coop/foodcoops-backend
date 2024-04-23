package de.dhbw.foodcoop.warehouse.plugins.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.admin.ConfigurationService;
import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;

@RestController
public class ConfigurationController {

	@Autowired
	private ConfigurationService service;
	
	@GetMapping("/configuration")
	public ConfigurationEntity getConfig() {
		return service.getConfig().orElseThrow();
	}
	
	@PutMapping
	public ConfigurationEntity updateConfig(ConfigurationEntity ce) {
		return service.updateConfig(ce);
	}
}
