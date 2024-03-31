package de.dhbw.foodcoop.warehouse.plugins.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

@RestController
public class BestellUebersichtController {

	@Autowired
	private BestellÜbersichtService bueService;
	
	@GetMapping("/bestellUebersicht/last")
	public BestellUebersicht getLastBestellUebersicht() {
		return bueService.getLastUebersicht();
	}
	
	@GetMapping("/bestellUebersicht/{id}")
	public BestellUebersicht getById(@PathVariable String id) {
		return bueService.findById(id);
	}
	
	@GetMapping("/bestellUebersicht/getByDeadline")
	public BestellUebersicht getByDeadline(@RequestBody Deadline deadline) {
		return bueService.getByDeadline(deadline);
	}
	
	@DeleteMapping("/bestellUebersicht/{id}")
	public void getByDeadline(@PathVariable String id) {
		 bueService.deleteById(id);
	}
}
