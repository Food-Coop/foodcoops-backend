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
    public List<Person> alle() {
        return springDataPersonRepository.findAll();
    }

    @Override
    public Person speichern(Person Person) {
        return springDataPersonRepository.save(Person);
    }

    @Override
    public Optional<Person> findeMitId(String id) {
        return springDataPersonRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataPersonRepository.deleteById(id);
    }
}
