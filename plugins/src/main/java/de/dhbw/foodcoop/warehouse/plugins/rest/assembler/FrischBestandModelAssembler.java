package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.FrischBestandController;

@Component
public class FrischBestandModelAssembler implements RepresentationModelAssembler<FrischBestandRepresentation, EntityModel<FrischBestandRepresentation>> {
    @Override
    public EntityModel<FrischBestandRepresentation> toModel(FrischBestandRepresentation frischBestand) {
        return EntityModel.of(frischBestand,
                linkTo(methodOn(FrischBestandController.class).one(frischBestand.getId())).withSelfRel(),
                linkTo(methodOn(FrischBestandController.class).all()).withRel("frischBestand"),
                linkTo(methodOn(FrischBestandController.class).newFrischBestand(frischBestand)).withSelfRel(),
                linkTo(methodOn(FrischBestandController.class).update(frischBestand, frischBestand.getId())).withSelfRel(),
                linkTo(methodOn(FrischBestandController.class).delete(frischBestand.getId())).withSelfRel());
    }
}