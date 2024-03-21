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

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestandToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestandMapper;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.ProduktModelAssembler;

@RestController
public class ProduktController {
    private final ProduktService service;
    private final RepresentationToBestandMapper toProdukt;
    private final BestandToRepresentationMapper toPresentation;
    private final ProduktModelAssembler assembler;

    @Autowired
    public ProduktController(ProduktService service, RepresentationToBestandMapper toProdukt, BestandToRepresentationMapper toPresentation, ProduktModelAssembler assembler) {
        this.service = service;
        this.toProdukt = toProdukt;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/produkte/{id}")
    public EntityModel<ProduktRepresentation> one(@PathVariable String id) {
        Produkt produkt = service.findById(id)
                .orElseThrow(() -> new ProduktNotFoundException(id));
        ProduktRepresentation presentation = (ProduktRepresentation) toPresentation.apply(produkt);
        return assembler.toModel(presentation);
    }

    @GetMapping("/produkte")
    public CollectionModel<EntityModel<ProduktRepresentation>> all() {
    	
     	List<Produkt> produkte = service.all();
    	List<EntityModel<ProduktRepresentation>> produkt = new ArrayList<>();
    	for(Produkt p : produkte) {
    		ProduktRepresentation pr = (ProduktRepresentation) toPresentation.apply(p);
    		EntityModel<ProduktRepresentation> em = assembler.toModel(pr);
    		produkt.add(em);
    	}
      
        return CollectionModel.of(produkt,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/produkte")
    public ResponseEntity<?> newProdukt(@RequestBody ProduktRepresentation newProdukt) {
        String id = newProdukt.getId() == null ||
                newProdukt.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newProdukt.getId();
        newProdukt.setId(id);
        Produkt produkt = service.save((Produkt) toProdukt.apply(newProdukt));
        EntityModel<ProduktRepresentation> entityModel = assembler.toModel((ProduktRepresentation) toPresentation.apply(produkt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/produkte/{id}")
    public ResponseEntity<?> update(@RequestBody ProduktRepresentation changedProdukt, @PathVariable String id) {
        Produkt oldProdukt = service.findById(id).orElseThrow(() -> new ProduktNotFoundException(id));
        Produkt updateProdukt = (Produkt) toProdukt.update(oldProdukt, changedProdukt);

        Produkt saved = service.save(updateProdukt);

        EntityModel<ProduktRepresentation> entityModel = assembler.toModel((ProduktRepresentation) toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/produkte/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws ProduktInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
