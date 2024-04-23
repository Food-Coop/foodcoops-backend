package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestandMapper;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestandModelAssembler;

@RestController
public class FrischBestandController {
    private final FrischBestandService service;
    private final RepresentationToBestandMapper toFrischBestand;
    private final BestandToRepresentationMapper toPresentation;
    private final FrischBestandModelAssembler assembler;

    @Autowired
    public FrischBestandController(FrischBestandService service, RepresentationToBestandMapper toFrischBestand, BestandToRepresentationMapper toPresentation, FrischBestandModelAssembler assembler) {
        this.service = service;
        this.toFrischBestand = toFrischBestand;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/frischBestand/{id}")
    public EntityModel<FrischBestandRepresentation> one(@PathVariable String id) {
        FrischBestand frischBestand = service.findById(id)
                .orElseThrow(() -> new FrischBestandNotFoundException(id));
        FrischBestandRepresentation presentation = (FrischBestandRepresentation) toPresentation.apply(frischBestand);
        return assembler.toModel(presentation);
    }

    @GetMapping("/frischBestand")
    public CollectionModel<EntityModel<FrischBestandRepresentation>> all() {
     	List<FrischBestand> frischBestellung = service.all();
    	List<EntityModel<FrischBestandRepresentation>> frisch = new ArrayList<>();
    	for(FrischBestand f : frischBestellung) {
    		FrischBestandRepresentation fr = (FrischBestandRepresentation) toPresentation.apply(f);
    		EntityModel<FrischBestandRepresentation> em = assembler.toModel(fr);
    		frisch.add(em);
    	}
    	

        return CollectionModel.of(frisch,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/frischBestand")
    public ResponseEntity<?> newFrischBestand(@RequestBody FrischBestandRepresentation newFrischBestand) {
        String id = newFrischBestand.getId() == null ||
                newFrischBestand.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newFrischBestand.getId();
        newFrischBestand.setId(id);
        FrischBestand produkt = service.save((FrischBestand) toFrischBestand.apply(newFrischBestand));
        EntityModel<FrischBestandRepresentation> entityModel = assembler.toModel((FrischBestandRepresentation) toPresentation.apply(produkt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/frischBestand/{id}")
    public ResponseEntity<?> update(@RequestBody FrischBestandRepresentation changedProdukt, @PathVariable String id) {
        FrischBestand oldFrischBestand = service.findById(id).orElseThrow(() -> new FrischBestandNotFoundException(id));
        FrischBestand updateProdukt = (FrischBestand) toFrischBestand.update(oldFrischBestand, changedProdukt);

        FrischBestand saved = service.save(updateProdukt);

        EntityModel<FrischBestandRepresentation> entityModel = assembler.toModel((FrischBestandRepresentation) toPresentation.apply(saved));

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
