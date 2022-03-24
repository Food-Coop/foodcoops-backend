package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.PersonRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;

@Component
public class PersonToRepresentationMapper implements Function<Person, PersonRepresentation> {
    @Override
    public PersonRepresentation apply(Person person) {
        return new PersonRepresentation(person.getId(), person.getVorname(), person.getNachname());
    }
}
