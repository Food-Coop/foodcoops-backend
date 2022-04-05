package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.exceptions.PersonInUseException;
import de.dhbw.foodcoop.warehouse.domain.repositories.PersonRepository;

@Service
public class PersonService {
    private final PersonRepository repository;
    private final FrischBestellungService frischBestellungService;

    @Autowired
    public PersonService(PersonRepository repository, FrischBestellungService frischBestellungService) {
        this.repository = repository;
        this.frischBestellungService = frischBestellungService;
    }

    public List<Person> all() {
        return repository.alle();
    }

    public Optional<Person> findById(String id) {
        return repository.findeMitId(id);
    }

    public Person save(Person newPerson) {
        return repository.speichern(newPerson);
    }

    public void deleteById(String id) {
        Optional<Person> toBeDeleted = repository.findeMitId(id);
        if (toBeDeleted.isEmpty()) {
            return;
        }
        // if (frischBestellungService.all().stream()
        //         .anyMatch(frischbestellung -> frischbestellung.getPerson().equals(toBeDeleted.get()))) {
        //     throw new PersonInUseException(id);
        // }
        repository.deleteById(id);
    }
}

