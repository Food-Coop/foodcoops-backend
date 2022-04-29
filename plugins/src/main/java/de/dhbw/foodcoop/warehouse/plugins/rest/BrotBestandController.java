package de.dhbw.foodcoop.warehouse.plugins.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BrotBestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.FrischBestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBrotBestandMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToFrischBestandMapper;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.BrotBestandModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestandModelAssembler;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BrotBestandController {
    private final BrotBestandService service;
    private final RepresentationToBrotBestandMapper toBrotBestand;
    private final BrotBestandToRepresentationMapper toPresentation;
    private final BrotBestandModelAssembler assembler;

    @Autowired
    public BrotBestandController(BrotBestandService service, RepresentationToBrotBestandMapper toBrotBestand, BrotBestandToRepresentationMapper toPresentation, BrotBestandModelAssembler assembler) {
        this.service = service;
        this.toBrotBestand = toBrotBestand;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/brotBestand/{id}")
    public EntityModel<BrotBestandRepresentation> one(@PathVariable String id) {
        BrotBestand brotBestand = service.findById(id)
                .orElseThrow(() -> new BrotBestandNotFoundException(id));
        BrotBestandRepresentation presentation = toPresentation.apply(brotBestand);
        return assembler.toModel(presentation);
    }

    @GetMapping("/brotBestand")
    public CollectionModel<EntityModel<BrotBestandRepresentation>> all() {
        List<EntityModel<BrotBestandRepresentation>> produkts = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(BrotBestandController.class).all()).withSelfRel());
    }

    @PostMapping("/brotBestand")
    public ResponseEntity<?> newFrischBestand(@RequestBody BrotBestandRepresentation newBrotBestand) {
        String id = newBrotBestand.getId() == null ||
                newBrotBestand.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newBrotBestand.getId();
            newBrotBestand.setId(id);
        BrotBestand brot = service.save(toBrotBestand.apply(newBrotBestand));
        EntityModel<BrotBestandRepresentation> entityModel = assembler.toModel(toPresentation.apply(brot));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/brotBestand/{id}")
    public ResponseEntity<?> update(@RequestBody BrotBestandRepresentation changedBrotBestand, @PathVariable String id) {
        BrotBestand oldBrotBestand = service.findById(id).orElseThrow(() -> new FrischBestandNotFoundException(id));
        BrotBestand updateBrotBestand = toBrotBestand.update(oldBrotBestand, changedBrotBestand);

        BrotBestand saved = service.save(updateBrotBestand);

        EntityModel<BrotBestandRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/brotBestand/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BrotBestandInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public Class<?> newBrotBestand(BrotBestandRepresentation brotBestand) {
        return null;
    }
}
