package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.repositories.PersonRepository;

@Repository
public class PersonRepositoryBridge implements PersonRepository {
	private final SpringDataPersonRepository springDataPersonRepository;
	
	@Autowired
	public PersonRepositoryBridge(SpringDataPersonRepository springDataPersonRepository) {
		this.springDataPersonRepository = springDataPersonRepository;
	}
	
	@Override
	public Person speichern(Person person) {
		// TODO Auto-generated method stub
		return springDataPersonRepository.save(person);
	}

	//NACH SERVICE AUSLADEN, BEI BESTELLUNG AKTUELLE BESTELLUNGEN LADEN KÖNNEN!


	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		springDataPersonRepository.deleteById(id);
	}



	@Override
	public Optional<Person> findeMitId(String personId) {
		// TODO Auto-generated method stub
		return springDataPersonRepository.findById(personId);
	}

}
