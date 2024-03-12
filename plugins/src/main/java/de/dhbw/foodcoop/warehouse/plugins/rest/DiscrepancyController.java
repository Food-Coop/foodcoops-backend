package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.diskrepanz.DiscrepancyService;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;

@RestController
public class DiscrepancyController {

	private final DiscrepancyService discrepancyService;
	
	@Autowired
	public DiscrepancyController(DiscrepancyService discrepancyService) {
		this.discrepancyService = discrepancyService;
	}
	
	
	@GetMapping
	public List<DiscrepancyEntity> getAll() {
		return discrepancyService.findAll();
	}
}
