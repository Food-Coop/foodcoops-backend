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

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestellungModelAssembler;

@RestController
public class FrischBestellungController {
    private final FrischBestellungService service;
    private final RepresentationToBestellungMapper toFrischBestellung;
    private final BestellungToRepresentationMapper toPresentation;
    private final FrischBestellungModelAssembler assembler;
    private final DeadlineService deadlineService;
    private final DeadlineToRepresentationMapper deadlineToPresentation;
    private final DeadlineModelAssembler deadlineAssembler;

    @Autowired
    public FrischBestellungController(FrischBestellungService service, RepresentationToBestellungMapper toFrischBestellung, BestellungToRepresentationMapper toPresentation, FrischBestellungModelAssembler assembler, DeadlineService deadlineService, DeadlineToRepresentationMapper deadlineToPresentation, DeadlineModelAssembler deadlineAssembler) {
        this.service = service;
        this.toFrischBestellung = toFrischBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
        this.deadlineService = deadlineService;
        this.deadlineToPresentation = deadlineToPresentation;
        this.deadlineAssembler = deadlineAssembler;
    }

    @GetMapping("/frischBestellung/{id}")
    public EntityModel<FrischBestellungRepresentation> one(@PathVariable String id) {
        FrischBestellung produkt = service.findById(id)
                .orElseThrow(() -> new FrischBestellungNotFoundException(id));
        FrischBestellungRepresentation presentation = (FrischBestellungRepresentation) toPresentation.apply(produkt);
        return assembler.toModel(presentation);
    }

    @GetMapping("/frischBestellung")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> all() {
    	
     	List<FrischBestellung> frischBestellungen = service.all();
    	List<EntityModel<FrischBestellungRepresentation>> frisch = new ArrayList<>();
    	for(FrischBestellung f : frischBestellungen) {
    		FrischBestellungRepresentation fr = (FrischBestellungRepresentation) toPresentation.apply(f);
    		EntityModel<FrischBestellungRepresentation> em = assembler.toModel(fr);
    		frisch.add(em);
    	}
       
        return CollectionModel.of(frisch,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/datum/{person_id}")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateAfterAndPerson(@PathVariable String person_id){
        //Timestamp datum1 = getTimestampNow();
    	Optional<Deadline> deadline = deadlineService.getByPosition(0);
    	if(deadline.isEmpty()) {
    		return null;
    	}
    	LocalDateTime datum = deadlineService.getByPosition(0).get().getDatum();
        
     	List<FrischBestellung> frischBestellungen = service.findByDateAfterAndPerson(datum, person_id);
    	List<EntityModel<FrischBestellungRepresentation>> frisch = new ArrayList<>();
    	for(FrischBestellung f : frischBestellungen) {
    		FrischBestellungRepresentation fr = (FrischBestellungRepresentation) toPresentation.apply(f);
    		EntityModel<FrischBestellungRepresentation> em = assembler.toModel(fr);
    		frisch.add(em);
    	}
   
        return CollectionModel.of(frisch,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/person/{person_id}")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateBetween(@PathVariable String person_id){
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
     	List<FrischBestellung> frischBestellungen = service.findByDateBetween(datum1, datum2, person_id);
    	List<EntityModel<FrischBestellungRepresentation>> frisch = new ArrayList<>();
    	for(FrischBestellung f : frischBestellungen) {
    		FrischBestellungRepresentation fr = (FrischBestellungRepresentation) toPresentation.apply(f);
    		EntityModel<FrischBestellungRepresentation> em = assembler.toModel(fr);
    		frisch.add(em);
    	}
      
        return CollectionModel.of(frisch,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/datum/menge")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
    	LocalDateTime datum = deadlineService.getByPosition(0).get().getDatum();
     	List<FrischBestellung> frischBestellungen = service.findByDateAfterAndSum(datum);
    	List<EntityModel<FrischBestellungRepresentation>> frisch = new ArrayList<>();
    	for(FrischBestellung f : frischBestellungen) {
    		FrischBestellungRepresentation fr = (FrischBestellungRepresentation) toPresentation.apply(f);
    		EntityModel<FrischBestellungRepresentation> em = assembler.toModel(fr);
    		frisch.add(em);
    	}
       
        return CollectionModel.of(frisch,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @PostMapping("/frischBestellung")
    public ResponseEntity<?> newFrischBestellung(@RequestBody FrischBestellungRepresentation newFrischBestellung) {
        String id = newFrischBestellung.getId() == null ||
                newFrischBestellung.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newFrischBestellung.getId();
        newFrischBestellung.setId(id);

        FrischBestellung frischBestellung = service.save((FrischBestellung) toFrischBestellung.apply(newFrischBestellung));
        EntityModel<FrischBestellungRepresentation> entityModel = assembler.toModel((FrischBestellungRepresentation) toPresentation.apply(frischBestellung));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/frischBestellung/{id}")
    public ResponseEntity<?> update(@RequestBody FrischBestellungRepresentation changedFrischBestellung, @PathVariable String id) {

        FrischBestellung oldFrischBestellung = service.findById(id).orElseThrow(() -> new FrischBestellungNotFoundException(id));
        FrischBestellung updateFrischBestellung = (FrischBestellung) toFrischBestellung.update(oldFrischBestellung, changedFrischBestellung);

        FrischBestellung saved = service.save(updateFrischBestellung);

        EntityModel<FrischBestellungRepresentation> entityModel = assembler.toModel((FrischBestellungRepresentation) toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/frischBestellung/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws FrischBestellungInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

   
}

