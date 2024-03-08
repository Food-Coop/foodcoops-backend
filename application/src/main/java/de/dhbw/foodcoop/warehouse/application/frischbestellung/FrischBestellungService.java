package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.einkauf.PersonService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Service
public class FrischBestellungService {
    private final FrischBestellungRepository repository;
    private final PersonService personService;

    @Autowired
    public FrischBestellungService(FrischBestellungRepository repository, PersonService personService) {
        this.repository = repository;
        this.personService = personService;
    }

    public List<FrischBestellung> all() {
        return repository.alle();
    }

    public List<FrischBestellung> findByDateAfterAndPerson(Timestamp datum, String person_id){
        return repository.findeMitDatumNachUndPerson(datum, person_id);
    }

    public List<FrischBestellung> findByDateAfterAndSum(Timestamp datum){
        return repository.findeMitDatumNachUndSum(datum);
    }

    public List<FrischBestellung> findByDateBetween(Timestamp datum1, Timestamp datum2, String person_id){
        return repository.findeMitDatumZwischen(datum1, datum2, person_id);
    }

    public FrischBestellung save(FrischBestellung bestellung) {
    	Person p = personService.getOrCreatePerson(bestellung.getPersonId());
    	
        FrischBestellung frischBestellung = repository.speichern(bestellung);
        p.getBestellungen().add(new EinkaufBestellungVergleich(UUID.randomUUID().toString(), frischBestellung, 0, false));
        personService.save(p);
        return frischBestellung;
    }

    public Optional<FrischBestellung> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}   
