package de.dhbw.foodcoop.warehouse.plugins.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.FrischBestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToFrischBestandMapper;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestandModelAssembler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FrischBestandController {
    private final FrischBestandService service;
    private final RepresentationToFrischBestandMapper toFrischBestand;
    private final FrischBestandToRepresentationMapper toPresentation;
    private final FrischBestandModelAssembler assembler;

    @Autowired
    public FrischBestandController(FrischBestandService service, RepresentationToFrischBestandMapper toFrischBestand, FrischBestandToRepresentationMapper toPresentation, FrischBestandModelAssembler assembler) {
        this.service = service;
        this.toFrischBestand = toFrischBestand;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/frischBestand/{id}")
    public EntityModel<FrischBestandRepresentation> one(@PathVariable String id) {
        FrischBestand frischBestand = service.findById(id)
                .orElseThrow(() -> new FrischBestandNotFoundException(id));
        FrischBestandRepresentation presentation = toPresentation.apply(frischBestand);
        return assembler.toModel(presentation);
    }

    @GetMapping("/frischBestand")
    public CollectionModel<EntityModel<FrischBestandRepresentation>> all() {
        List<EntityModel<FrischBestandRepresentation>> produkts = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/frischBestand")
    public ResponseEntity<?> newFrischBestand(@RequestBody FrischBestandRepresentation newFrischBestand) {
        String id = newFrischBestand.getId() == null ||
                newFrischBestand.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newFrischBestand.getId();
        newFrischBestand.setId(id);
        FrischBestand produkt = service.save(toFrischBestand.apply(newFrischBestand));
        EntityModel<FrischBestandRepresentation> entityModel = assembler.toModel(toPresentation.apply(produkt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/frischBestand/{id}")
    public ResponseEntity<?> update(@RequestBody FrischBestandRepresentation changedProdukt, @PathVariable String id) {
        FrischBestand oldFrischBestand = service.findById(id).orElseThrow(() -> new FrischBestandNotFoundException(id));
        FrischBestand updateProdukt = toFrischBestand.update(oldFrischBestand, changedProdukt);

        FrischBestand saved = service.save(updateProdukt);

        EntityModel<FrischBestandRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/frischBestand/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws FrischBestandInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
