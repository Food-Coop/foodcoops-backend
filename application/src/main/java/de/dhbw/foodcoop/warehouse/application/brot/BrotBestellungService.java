package de.dhbw.foodcoop.warehouse.application.brot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;

@Service
public class BrotBestellungService {
    private final BrotBestellungRepository repository;
    @Autowired
    public BrotBestellungService(BrotBestellungRepository repository) {
        this.repository = repository;
    }

    public List<BrotBestellung> all() {
        return repository.alle();
    }
    
    public List<BrotBestellung> findAllAftetDate(LocalDateTime date) {
    	return repository.findAllOrdersAfterDate(date);
    }

    public List<BrotBestellung> findByDateAfterAndPerson(LocalDateTime datum, String person_id){
        return repository.findeMitDatumNachUndPerson(datum, person_id);
    }

    public List<BrotBestellung> findByDateAfterAndSum(LocalDateTime datum){
        return repository.findeMitDatumNachUndSum(datum);
    }

    public List<BrotBestellung> findByDateBetween(LocalDateTime datum1, LocalDateTime datum2, String person_id){
        return repository.findeMitDatumZwischen(datum1, datum2, person_id);
    }
    
    public List<BrotBestellung> findByDateBetween(LocalDateTime datum1, LocalDateTime datum2){
        return repository.findeMitDatumZwischen(datum1, datum2);
    }

    public BrotBestellung save(BrotBestellung bestellung) {
    	//Person p = personService.getOrCreatePerson(bestellung.getPersonId());

    	bestellung.setDatum(LocalDateTime.now());
        BrotBestellung brotBestellung = repository.speichern(bestellung);
       // EinkaufBestellungVergleich ebv = einkaufBestellungVergleichRepository.speichern(new EinkaufBestellungVergleich(UUID.randomUUID().toString(), brotBestellung, 0, false));
      //  p.getBestellungen().add(brotBestellung);
      //  personService.save(p);
        return brotBestellung;
    }

    public Optional<BrotBestellung> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
    
    
}   
