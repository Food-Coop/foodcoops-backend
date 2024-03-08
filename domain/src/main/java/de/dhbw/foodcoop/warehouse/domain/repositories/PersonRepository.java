package de.dhbw.foodcoop.warehouse.domain.repositories;

import java.util.List;
import java.util.Optional;

import de.dhbw.foodcoop.warehouse.domain.entities.Person;

public interface PersonRepository {

	Person speichern(Person person);
	
	
	void deleteById(String id);
	
	
	Optional<Person> findeMitId(String personId);
	

}
