package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.application.diskrepanz.DiscrepancyService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.CategoryAndPercentHolder;

@RestController
public class DiscrepancyController {

	private final DiscrepancyService discrepancyService;
	
	@Autowired
	private GebindemanagementService service;
	
	@Autowired
	private BestellÜbersichtService bestellService;
	@Autowired
	private FrischBestellungService frischService;
	
	
	@Autowired
	public DiscrepancyController(DiscrepancyService discrepancyService) {
		this.discrepancyService = discrepancyService;
	}
	
	
	@PostMapping("/gebinde/discrepancy/listForMixableCategorie")
	public List<DiscrepancyEntity> getDiscrepancyListForMixableCategorie(@RequestBody CategoryAndPercentHolder categoryAndPercent) {
		return service.getDiscrepancyListForMixableCategorie(categoryAndPercent.getKategorie(), categoryAndPercent.getPercentage());
	}
	
	@PostMapping("/gebinde/discrepancy/forNonMixableOrder")
	public List<DiscrepancyEntity> getDiscrepancyForNonMixableOrder(@RequestBody CategoryAndPercentHolder orderAndPercentHolder) {
		return service.getDiscrepancyForNotMixableOrder(orderAndPercentHolder.getKategorie(), orderAndPercentHolder.getPercentage());
	}
	
	@PostMapping("/gebinde/discrepancy/autoDecide")
	public List<DiscrepancyEntity> getDiscrepancyForBoth(@RequestBody CategoryAndPercentHolder categoryAndPercent) {
		if(categoryAndPercent.getKategorie().isMixable()) {
			return service.getDiscrepancyListForMixableCategorie(categoryAndPercent.getKategorie(), categoryAndPercent.getPercentage());
		} else {
			return service.getDiscrepancyForNotMixableOrder(categoryAndPercent.getKategorie(), categoryAndPercent.getPercentage());
		}
	}
	
	@PutMapping("/gebinde/discrepancy/update/tooMuchTooLittle/{id}")
	public ResponseEntity<DiscrepancyEntity> updatedDiscrepancy(@PathVariable String id, @RequestBody String body) {
		
		 float zuVielZuWenig;
		    try {
		        zuVielZuWenig = Float.parseFloat(body);
		    } catch (NumberFormatException e) {
		        return ResponseEntity.badRequest().build();
		    }
		    
		Optional<DiscrepancyEntity> e = discrepancyService.findById(id);
		if(e.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		DiscrepancyEntity de = e.get();
		de.setZuVielzuWenig(zuVielZuWenig);
		
		return ResponseEntity.status(HttpStatus.OK).body(discrepancyService.save(de));
	}
	
	@PutMapping("/gebinde/discrepancy/update/gebindeAmountToOrder/{id}")
	public ResponseEntity<DiscrepancyEntity> updatedZuBestellen(@PathVariable String id, @RequestBody String body) {
		
		 double zuBestellendeGebinde;
		    try {
		    	zuBestellendeGebinde = Double.parseDouble(body);
		    } catch (NumberFormatException e) {
		        return ResponseEntity.badRequest().build();
		    }
		    
		Optional<DiscrepancyEntity> e = discrepancyService.findById(id);
		if(e.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		DiscrepancyEntity de = e.get();
		de.setZuBestellendeGebinde(zuBestellendeGebinde);
		if(de.getBestand() instanceof FrischBestand) {
			FrischBestand bestand = (FrischBestand) de.getBestand();
			de.setZuVielzuWenig((float)de.getZuBestellendeGebinde() * bestand.getGebindegroesse() - de.getGewollteMenge());
			
			return ResponseEntity.status(HttpStatus.OK).body(discrepancyService.save(de));
		}
		return ResponseEntity.unprocessableEntity().build();
	}
	
	@PostMapping("/gebinde/discrepancy/add")
	public ResponseEntity<BestellUebersicht> addDiscrepancyToLastOrderList(@RequestBody DiscrepancyEntity body) {
		if(body.getId() == null || body.getId().equalsIgnoreCase("undefined")) {
			body.setId(UUID.randomUUID().toString());
		}
		BestellUebersicht bue = bestellService.getLastUebersicht();
		if(bue == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		List<DiscrepancyEntity> list = bue.getDiscrepancy();
		if(list.stream().anyMatch(t -> body.getBestand().getId().equalsIgnoreCase(t.getBestand().getId()))) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		if(body.getBestand() == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		list.add(body);
		bue = bestellService.update(bue);
		return ResponseEntity.ok().body(bue);
		
	}
	
	@GetMapping("/gebinde")
	public List<DiscrepancyEntity> getAll() {
		return discrepancyService.findAll();
	}
}
