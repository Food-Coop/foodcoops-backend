package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.PersonRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.PersonController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<PersonRepresentation, EntityModel<PersonRepresentation>> {

    @Override
    public EntityModel<PersonRepresentation> toModel(PersonRepresentation person) {
        return EntityModel.of(person,
                linkTo(methodOn(PersonController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("person"),
                linkTo(methodOn(PersonController.class).newPerson(person)).withRel("post"),
                linkTo(methodOn(PersonController.class).update(person, person.getId())).withRel("update"),
                linkTo(methodOn(PersonController.class).delete(person.getId())).withRel("delete"));
    }
}
