package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestellungService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.BrotBestellungModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;

@RestController
public class BrotBestellungController {
    private final BrotBestellungService service;
    private final RepresentationToBestellungMapper toBrotBestellung;
    private final BestellungToRepresentationMapper toPresentation;
    private final BrotBestellungModelAssembler assembler;
    private final DeadlineService deadlineService;
    private final DeadlineToRepresentationMapper deadlineToPresentation;
    private final DeadlineModelAssembler deadlineAssembler;

    @Autowired
    public BrotBestellungController(BrotBestellungService service, RepresentationToBestellungMapper toBrotBestellung, BestellungToRepresentationMapper toPresentation, BrotBestellungModelAssembler assembler, DeadlineService deadlineService, DeadlineToRepresentationMapper deadlineToPresentation, DeadlineModelAssembler deadlineAssembler) {
        this.service = service;
        this.toBrotBestellung = toBrotBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
        this.deadlineService = deadlineService;
        this.deadlineToPresentation = deadlineToPresentation;
        this.deadlineAssembler = deadlineAssembler;
    }

    @GetMapping("/brotBestellung/{id}")
    public EntityModel<BrotBestellungRepresentation> one(@PathVariable String id) {
        BrotBestellung brot = service.findById(id)
                .orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellungRepresentation presentation = (BrotBestellungRepresentation)toPresentation.apply(brot);
        return assembler.toModel(presentation);
    }

    @GetMapping("/brotBestellung")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> all() {
       	List<BrotBestellung> brotBestellungen = service.all();
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}

        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndPerson(@PathVariable String person_id){
        //Timestamp datum1 = getTimestampNow();
    	Optional<Deadline> deadline = deadlineService.getByPosition(0);
    	if(deadline.isEmpty()) {
    		return null;
    	}
    	LocalDateTime datum = deadlineService.getByPosition(0).get().getDatum();
     	List<BrotBestellung> brotBestellungen = service.findByDateAfterAndPerson(datum, person_id);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
      
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/person/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateBetween(@PathVariable String person_id){
    	Optional<Deadline> date1 = deadlineService.getByPosition(0);
    	Optional<Deadline> date2 = deadlineService.getByPosition(1);
    	
    	if(date1.isEmpty()) {
    		return null;
    	}
    	if(date2.isEmpty()) {
    		return findByDateAfterAndPerson(person_id);
    	}
    	
    	LocalDateTime datum1 = date1.get().getDatum();
    	LocalDateTime datum2 = date2.get().getDatum();
     	List<BrotBestellung> brotBestellungen = service.findByDateBetween(datum1, datum2, person_id);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
      
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/menge")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
    	LocalDateTime datum = deadlineService.getByPosition(0).get().getDatum();
     	List<BrotBestellung> brotBestellungen = service.findByDateAfterAndSum(datum);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
     
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @PostMapping("/brotBestellung")
    public ResponseEntity<?> newBrotBestellung(@RequestBody BrotBestellungRepresentation newBrotBestellung) {
        String id = newBrotBestellung.getId() == null ||
                newBrotBestellung.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newBrotBestellung.getId();
        newBrotBestellung.setId(id);
        BrotBestellung brotBestellung = service.save((BrotBestellung)toBrotBestellung.apply(newBrotBestellung));
        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel((BrotBestellungRepresentation) toPresentation.apply(brotBestellung));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/brotBestellung/{id}")
    public ResponseEntity<?> update(@RequestBody BrotBestellungRepresentation brotBestellung, @PathVariable String id) {

        BrotBestellung oldBrotBestellung = service.findById(id).orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellung updateBrotBestellung = (BrotBestellung) toBrotBestellung.update(oldBrotBestellung, brotBestellung);

        BrotBestellung saved = service.save(updateBrotBestellung);

        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel((BrotBestellungRepresentation) toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/brotBestellung/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BrotBestellungInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
   
}

