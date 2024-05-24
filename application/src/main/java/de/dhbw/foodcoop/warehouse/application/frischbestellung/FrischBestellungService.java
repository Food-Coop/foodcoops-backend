package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;

@Service
public class FrischBestellungService {
    private final FrischBestellungRepository repository;
    
    @Autowired
    private DeadlineService deadlineService;
    

    @Autowired
    public FrischBestellungService(FrischBestellungRepository repository) {
        this.repository = repository;
    }

    public List<FrischBestellung> all() {
        return repository.alle();
    }

    public List<FrischBestellung> findAllOrdersAfterDate(LocalDateTime date) {
    	return repository.findeAlleBestellungenNachDatum(date);
    }
    public List<FrischBestellung> findByDateAfterAndPerson(LocalDateTime datum, String person_id){
        return repository.findeMitDatumNachUndPerson(datum, person_id);
    }

    public List<FrischBestellung> findByDateAfterAndSum(LocalDateTime datum){
        return repository.findeMitDatumNachUndSum(datum);
    }

    public List<FrischBestellung> findByDateBetween(LocalDateTime datum1, LocalDateTime datum2, String person_id){
        return repository.findeMitDatumZwischen(datum1, datum2, person_id);
    }
    
    public List<FrischBestellung> findByDateBetween(LocalDateTime datum1, LocalDateTime datum2){
        return repository.findeMitDatumZwischen(datum1, datum2);
    }
    
    public double orderAmountForLastWeek(BestandEntity b) {
    	if(deadlineService.getByPosition(0).isPresent() && deadlineService.getByPosition(1).isPresent()) {
    		return findByDateBetween(deadlineService.getByPosition(0).get().getDatum(), deadlineService.getByPosition(1).get().getDatum())
    		.stream().filter(t -> t.getFrischbestand().getId().equalsIgnoreCase(b.getId())).mapToDouble(t-> t.getBestellmenge()).sum();
    	
    	}
		return 0;
    }

    //Hier wird für den Einkauf direkt ein Vergleichs Objekt angelegt
    public FrischBestellung save(FrischBestellung bestellung) {
    //	Person p = personService.getOrCreatePerson(bestellung.getPersonId());

    	bestellung.setDatum(LocalDateTime.now());
        FrischBestellung frischBestellung = repository.speichern(bestellung);
       // EinkaufBestellungVergleich ebv = einkaufBestellungVergleichRepository.speichern(new EinkaufBestellungVergleich(UUID.randomUUID().toString(), frischBestellung, 0, false));
     //   p.getBestellungen().add(frischBestellung);
     //   personService.save(p);
        return frischBestellung;
    }

    public Optional<FrischBestellung> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}   
