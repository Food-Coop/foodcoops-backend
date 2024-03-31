package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.brot.BrotBestellungService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.diskrepanz.DiscrepancyService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.application.gebindemanagement.GebindemanagementService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.BestellÜbersichtRepository;

@Service
public class BestellÜbersichtService {

	@Autowired
	private BestellÜbersichtRepository repo;
	
	@Autowired
	private GebindemanagementService gebindeService;
	
	
	@Autowired
	private DeadlineService deadlineService;
	
	@Autowired
	private FrischBestellungService frischService;
	
	@Autowired
	private BrotBestellungService brotService;
	
	public BestellUebersicht getLastUebersicht() {
		Deadline d = deadlineService.last();
		return repo.findeMitDeadline(d);
	}
	
	public BestellUebersicht getByDeadline(Deadline d) {
		return repo.findeMitDeadline(d);
	}
	
	public void deleteById(String id) {
		repo.deleteById(id);
	}
	
	public BestellUebersicht findById(String id) {
		
		
		return repo.findeMitId(id).orElseThrow();
	}
	
	public Optional<BestellUebersicht> createList(int threshold) {
		BestellUebersicht bestellÜbersicht = new BestellUebersicht();
		bestellÜbersicht.setId(UUID.randomUUID().toString());
		
		List<DiscrepancyEntity> discrepancy = new ArrayList<>();
		//neuster Eintrag
		Deadline neuErstellte = deadlineService.last();
		bestellÜbersicht.setToOrderWithinDeadline(neuErstellte);
		
		//Brauchen eins vorher 
		Optional<Deadline> vorletzte = deadlineService.getByPosition(1);
		if(vorletzte.isEmpty()) {
			return Optional.empty();
		}
		
		Set<Kategorie> findAllCategoriesOrderedLastWeek = new HashSet<>();
		List<FrischBestellung> frischBestellungen = frischService.findAllOrdersAfterDate(vorletzte.get().getDatum());
		frischBestellungen.stream().forEach(f -> findAllCategoriesOrderedLastWeek.add(f.getFrischbestand().getKategorie()));
	
		for(Kategorie kategorie : findAllCategoriesOrderedLastWeek) {
			if(kategorie.isMixable()) {
				discrepancy.addAll(gebindeService.getDiscrepancyListForMixableCategorie(kategorie, threshold));
			} else {
				discrepancy.addAll(gebindeService.getDiscrepancyForNotMixableOrder(kategorie, threshold));
			}
		}
		
		bestellÜbersicht.setDiscrepancy(discrepancy);
		List<BrotBestellung> brotBestellungen = brotService.findAllAftetDate(vorletzte.get().getDatum());
		
		bestellÜbersicht.setBrotBestellung(brotBestellungen);
		System.out.println("ID: " + bestellÜbersicht.getId());
		return Optional.of(repo.speichern(bestellÜbersicht));
		
	}
	
}
