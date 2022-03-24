package de.dhbw.foodcoop.warehouse.domain.repositories;

import de.dhbw.foodcoop.warehouse.domain.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    List<Person> alle();

    Person speichern(Person person);

    Optional<Person> findeMitId(String id);

    void deleteById(String id);
}
