package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.PersonRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Service
public class PersonService {


    
    private final PersonRepository personRepo;
    
    @Autowired
    public PersonService(
			PersonRepository personRepo) {
		super();

		this.personRepo = personRepo;
	}
    public Person getOrCreatePerson(String personId) {
    	return personRepo.findeMitId(personId)
    		    .orElse(personRepo.speichern(new Person(personId, new ArrayList<EinkaufBestellungVergleich>())));
    }
    

	public void save(Person p) {
		// TODO Auto-generated method stub
		personRepo.speichern(p);
		
	}
	public Optional<Person> findById(String personId) {
		// TODO Auto-generated method stub
		return personRepo.findeMitId(personId);
	}
    
 
    
}
