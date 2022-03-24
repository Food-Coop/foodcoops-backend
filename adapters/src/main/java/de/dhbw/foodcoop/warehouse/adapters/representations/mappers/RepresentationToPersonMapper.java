package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.PersonRepresentation;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.PersonService;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.exceptions.PersonNotFoundException;

@Component
public class RepresentationToPersonMapper implements Function<PersonRepresentation, Person> {
    private final PersonService service;

    @Autowired
    public RepresentationToPersonMapper(PersonService service) {
        this.service = service;
    }

    @Override
    public Person apply(PersonRepresentation personRepresentation) {
        return new Person(personRepresentation.getId(), personRepresentation.getVorname(), personRepresentation.getNachname());
    }

    public Person applyById(String personId) {
        return service.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
    }

    public Person update(Person oldPerson, PersonRepresentation newPerson) {

        return new Person(
                oldPerson.getId(),
                pickNewIfDefined(oldPerson.getVorname(), newPerson.getVorname()),
                pickNewIfDefined(oldPerson.getNachname(), newPerson.getNachname())
        );
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }
}
