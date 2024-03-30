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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufCreateRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.EinkaufCreateToEntityMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.EinkaufToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToEinkaufMapper;
import de.dhbw.foodcoop.warehouse.application.einkauf.EinkaufService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandInUseException;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.BestandBuyCreator;
import de.dhbw.foodcoop.warehouse.plugins.helpObjects.EinkaufRequest;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.EinkaufModelAssembler;

@RestController
public class EinkaufController {
	
    private final EinkaufService einkaufService;
    
    @Autowired
    private  RepresentationToEinkaufMapper toEinkauf;
    
    @Autowired
    private  EinkaufToRepresentationMapper toPresentation;
    
    @Autowired
    private  EinkaufCreateToEntityMapper createMapper;
    
    @Autowired
    private  EinkaufModelAssembler assembler;
    

    @Autowired
    public EinkaufController(EinkaufService einkaufService) {
    	this.einkaufService = einkaufService;
    }
    

    @GetMapping("/einkauf")
    public CollectionModel<EntityModel<EinkaufRepresentation>> all() {
    	
     	List<EinkaufEntity> einkauf = einkaufService.all();
    	List<EntityModel<EinkaufRepresentation>> e = new ArrayList<>();
    	for(EinkaufEntity f : einkauf) {
    		EinkaufRepresentation fr = (EinkaufRepresentation) toPresentation.apply(f);
    		EntityModel<EinkaufRepresentation> em = assembler.toModel(fr);
    		e.add(em);
    	}
       
        return CollectionModel.of(e,
                linkTo(methodOn(EinkaufController.class).all()).withSelfRel());
    }
    
    @GetMapping("/einkauf/{id}")
    public EntityModel<EinkaufRepresentation> one(@PathVariable String id) {
    	EinkaufEntity einkauf = einkaufService.findById(id);
    	EinkaufRepresentation presentation = (EinkaufRepresentation) toPresentation.apply(einkauf);
        return assembler.toModel(presentation);
    }


    
    @PostMapping(value = "/einkaufe/create/bestandBuyObject")
    public BestandBuyEntity getBBEFromData(@RequestBody BestandBuyCreator creator) {
    	return einkaufService.createBestandBuyEntityForPersonOrder(creator.getBestandEntity(), creator.getAmount());
    } 
    
    
    @PostMapping("/einkauf")
    public ResponseEntity<?> executeShopping(@RequestBody EinkaufCreateRepresentation newEinkauf) {
        String id = newEinkauf.getId() == null ||
        		newEinkauf.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                	newEinkauf.getId();
        newEinkauf.setId(id);
        EinkaufEntity e = createMapper.apply(newEinkauf);
        EinkaufEntity einkauf;
		try {
			einkauf = einkaufService.einkaufDurchführen(e.getPersonId(), e.getBestellungsEinkauf(), e.getBestandEinkauf());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e1.getCause().toString());
		}
        EntityModel<EinkaufRepresentation> entityModel = assembler.toModel( toPresentation.apply(einkauf));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    
    
    @DeleteMapping("/einkauf/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        einkaufService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
    
}
