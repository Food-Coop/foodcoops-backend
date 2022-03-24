package de.dhbw.foodcoop.warehouse.plugins.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.adapters.representations.PersonRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.PersonToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToPersonMapper;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.PersonService;
import de.dhbw.foodcoop.warehouse.domain.entities.Person;
import de.dhbw.foodcoop.warehouse.domain.exceptions.PersonNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.PersonModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {
    private final PersonService service;
    private final PersonToRepresentationMapper toRepresentation;
    private final RepresentationToPersonMapper toPerson;
    private final PersonModelAssembler assembler;

    @Autowired
    public PersonController(PersonService service, PersonToRepresentationMapper toRepresentation, RepresentationToPersonMapper toPerson, PersonModelAssembler assembler) {
        this.service = service;
        this.toRepresentation = toRepresentation;
        this.toPerson = toPerson;
        this.assembler = assembler;
    }

    @GetMapping("/person/{id}")
    public EntityModel<PersonRepresentation> one(@PathVariable String id) {
        Person Person = service.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        return assembler.toModel(toRepresentation.apply(Person));
    }

    @GetMapping("/person")
    public CollectionModel<EntityModel<PersonRepresentation>> all() {
        List<EntityModel<PersonRepresentation>> Persons = service.all().stream()
                .map(toRepresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(Persons,
                linkTo(methodOn(PersonController.class).all()).withSelfRel());
    }

    @PostMapping("/person")
    public ResponseEntity<?> newPerson(@RequestBody PersonRepresentation newPerson) {
        String id = newPerson.getId() == null ||
                newPerson.getId().isBlank() ||
                newPerson.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newPerson.getId();
        newPerson.setId(id);
        Person saved = service.save(toPerson.apply(newPerson));
        EntityModel<PersonRepresentation> entityModel = assembler.toModel(toRepresentation.apply(saved));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/person/{id}")
    public ResponseEntity<?> update(@RequestBody PersonRepresentation newPerson, @PathVariable String id) {

        Person oldPerson = service.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        Person updatedPerson = toPerson.update(oldPerson, newPerson);

        Person saved = service.save(updatedPerson);

        EntityModel<PersonRepresentation> entityModel = assembler.toModel(toRepresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<?> delete(@PathVariable String id)  {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}