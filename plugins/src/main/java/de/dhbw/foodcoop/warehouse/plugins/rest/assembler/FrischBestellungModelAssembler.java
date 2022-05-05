package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.FrischBestellungController;

@Component
public class FrischBestellungModelAssembler implements RepresentationModelAssembler<FrischBestellungRepresentation, EntityModel<FrischBestellungRepresentation>> {
    @Override
    public EntityModel<FrischBestellungRepresentation> toModel(FrischBestellungRepresentation frischBestellung) {
        return EntityModel.of(frischBestellung,
                linkTo(methodOn(FrischBestellungController.class).one(frischBestellung.getId())).withSelfRel(),
                linkTo(methodOn(FrischBestellungController.class).all()).withRel("frischBestellung"),
                linkTo(methodOn(FrischBestellungController.class).newFrischBestellung(frischBestellung)).withSelfRel(),
                linkTo(methodOn(FrischBestellungController.class).update(frischBestellung, frischBestellung.getId())).withSelfRel(),
                linkTo(methodOn(FrischBestellungController.class).delete(frischBestellung.getId())).withSelfRel());
    }
}
