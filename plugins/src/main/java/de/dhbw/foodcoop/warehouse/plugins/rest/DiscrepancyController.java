package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.diskrepanz.DiscrepancyService;
import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.CategoryAndPercentHolder;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.OrderAndPercentHolder;

@RestController
public class DiscrepancyController {

	private final DiscrepancyService discrepancyService;
	
	@Autowired
	private GebindemanagementService service;
	
	
	@Autowired
	public DiscrepancyController(DiscrepancyService discrepancyService) {
		this.discrepancyService = discrepancyService;
	}
	
	
	@PostMapping("/gebinde/DiscrepancyListForMixableCategorie")
	public List<DiscrepancyEntity> getDiscrepancyListForMixableCategorie(@RequestBody CategoryAndPercentHolder categoryAndPercent) {
		return service.getDiscrepancyListForMixableCategorie(categoryAndPercent.getKategorie(), categoryAndPercent.getPercentage());
	}
	
	@PostMapping("/gebinde/DiscrepancyForNonMixableOrder")
	public DiscrepancyEntity getDiscrepancyForNonMixableOrder(@RequestBody OrderAndPercentHolder orderAndPercentHolder) {
		return service.getDiscrepancyForNotMixableOrder(orderAndPercentHolder.getBestellung(), orderAndPercentHolder.getPercentage());
	}
	
	@GetMapping("/gebinde")
	public List<DiscrepancyEntity> getAll() {
		return discrepancyService.findAll();
	}
}
