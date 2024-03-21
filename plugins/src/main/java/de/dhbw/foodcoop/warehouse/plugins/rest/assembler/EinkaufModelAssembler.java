package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.EinkaufController;
import de.dhbw.foodcoop.warehouse.plugins.rest.FrischBestandController;
import de.dhbw.foodcoop.warehouse.plugins.rest.FrischBestellungController;

@Component
public class EinkaufModelAssembler implements RepresentationModelAssembler<EinkaufRepresentation, EntityModel<EinkaufRepresentation>> {


	@Override
	public EntityModel<EinkaufRepresentation> toModel(EinkaufRepresentation einkauf) {
		// TODO Auto-generated method stub
		return EntityModel.of(einkauf,
				linkTo(methodOn(EinkaufController.class).one(einkauf.getId())).withSelfRel(),
				linkTo(methodOn(EinkaufController.class).delete(einkauf.getId())).withSelfRel(),
				linkTo(methodOn(EinkaufController.class).all()).withRel("einkauf"));
	}
}
