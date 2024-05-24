package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestandMapper;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.BrotBestandModelAssembler;

@RestController
public class BrotBestandController {
    private final BrotBestandService service;
    private final RepresentationToBestandMapper toBrotBestand;
    private final BestandToRepresentationMapper toPresentation;
    private final BrotBestandModelAssembler assembler;

    @Autowired
    public BrotBestandController(BrotBestandService service, RepresentationToBestandMapper toBrotBestand, BestandToRepresentationMapper toPresentation, BrotBestandModelAssembler assembler) {
        this.service = service;
        this.toBrotBestand = toBrotBestand;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/brotBestand/{id}")
    public EntityModel<BrotBestandRepresentation> one(@PathVariable String id) {
        BrotBestand brotBestand = service.findById(id)
                .orElseThrow(() -> new BrotBestandNotFoundException(id));
        BrotBestandRepresentation presentation = (BrotBestandRepresentation) toPresentation.apply(brotBestand);
        return assembler.toModel(presentation);
    }

    @GetMapping("/brotBestand")
    public CollectionModel<EntityModel<BrotBestandRepresentation>> all() {
    	List<BrotBestand> brotBestande = service.all();
    	List<EntityModel<BrotBestandRepresentation>> brote = new ArrayList<>();
    	for(BrotBestand b : brotBestande) {
    		BrotBestandRepresentation br = (BrotBestandRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestandRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
    	
 
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestandController.class).all()).withSelfRel());
    }

    @PostMapping("/brotBestand")
    public ResponseEntity<?> newBrotBestand(@RequestBody BrotBestandRepresentation newBrotBestand) {
        String id = newBrotBestand.getId() == null ||
                newBrotBestand.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newBrotBestand.getId();
            newBrotBestand.setId(id);
        BrotBestand brot = service.save((BrotBestand)toBrotBestand.apply(newBrotBestand));
        EntityModel<BrotBestandRepresentation> entityModel = assembler.toModel((BrotBestandRepresentation)toPresentation.apply(brot));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/brotBestand/{id}")
    public ResponseEntity<?> update(@RequestBody BrotBestandRepresentation changedBrotBestand, @PathVariable String id) {
        BrotBestand oldBrotBestand = service.findById(id).orElseThrow(() -> new BrotBestandNotFoundException(id));
        BrotBestand updateBrotBestand = (BrotBestand)toBrotBestand.update(oldBrotBestand, changedBrotBestand);

        BrotBestand saved = service.save(updateBrotBestand);

        EntityModel<BrotBestandRepresentation> entityModel = assembler.toModel((BrotBestandRepresentation)toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/brotBestand/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BrotBestandInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
