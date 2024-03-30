package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;

@Service
public class FrischBestellungService {
    private final FrischBestellungRepository repository;

    @Autowired
    public FrischBestellungService(FrischBestellungRepository repository) {
        this.repository = repository;
    }

    public List<FrischBestellung> all() {
        return repository.alle();
    }

    public List<FrischBestellung> findAllOrdersAfterDate(Timestamp date) {
    	return repository.findeAlleBestellungenNachDatum(date);
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

    //Hier wird für den Einkauf direkt ein Vergleichs Objekt angelegt
    public FrischBestellung save(FrischBestellung bestellung) {
    //	Person p = personService.getOrCreatePerson(bestellung.getPersonId());
        ZonedDateTime jetztInDeutschland = ZonedDateTime.now(ZoneId.of("Europe/Berlin"));
        long timestamp = jetztInDeutschland.toInstant().toEpochMilli();
        Timestamp t = new Timestamp(timestamp);
        t.setHours(LocalDateTime.now().getHour());
    	bestellung.setDatum(t);
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
