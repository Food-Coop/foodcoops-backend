package de.dhbw.foodcoop.warehouse.application.brot;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.einkauf.PersonService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufBestellungVergleichRepository;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Service
public class BrotBestellungService {
    private final BrotBestellungRepository repository;
    private final PersonService personService;
    private final EinkaufBestellungVergleichRepository einkaufBestellungVergleichRepository;
    @Autowired
    public BrotBestellungService(BrotBestellungRepository repository, PersonService personService, EinkaufBestellungVergleichRepository einkaufBestellungVergleichRepository) {
        this.repository = repository;
        this.personService = personService;
        this.einkaufBestellungVergleichRepository = einkaufBestellungVergleichRepository;
    }

    public List<BrotBestellung> all() {
        return repository.alle();
    }

    public List<BrotBestellung> findByDateAfterAndPerson(Timestamp datum, String person_id){
        return repository.findeMitDatumNachUndPerson(datum, person_id);
    }

    public List<BrotBestellung> findByDateAfterAndSum(Timestamp datum){
        return repository.findeMitDatumNachUndSum(datum);
    }

    public List<BrotBestellung> findByDateBetween(Timestamp datum1, Timestamp datum2, String person_id){
        return repository.findeMitDatumZwischen(datum1, datum2, person_id);
    }

    public BrotBestellung save(BrotBestellung bestellung) {
    	Person p = personService.getOrCreatePerson(bestellung.getPersonId());
    	
        BrotBestellung brotBestellung = repository.speichern(bestellung);
        EinkaufBestellungVergleich ebv = einkaufBestellungVergleichRepository.speichern(new EinkaufBestellungVergleich(UUID.randomUUID().toString(), brotBestellung, 0, false));
        p.getBestellungen().add(ebv);
        personService.save(p);
        return brotBestellung;
    }

    public Optional<BrotBestellung> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}   
