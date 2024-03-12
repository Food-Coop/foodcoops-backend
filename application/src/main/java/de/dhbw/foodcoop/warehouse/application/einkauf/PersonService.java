package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.repositories.PersonRepository;

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
    		    .orElse(personRepo.speichern(new Person(personId, new ArrayList<BestellungEntity>())));
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
