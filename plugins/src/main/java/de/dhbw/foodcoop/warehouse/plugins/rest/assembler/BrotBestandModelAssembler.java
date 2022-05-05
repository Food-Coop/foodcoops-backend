package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.BrotBestandController;

@Component
public class BrotBestandModelAssembler implements RepresentationModelAssembler<BrotBestandRepresentation, EntityModel<BrotBestandRepresentation>> {
    @Override
    public EntityModel<BrotBestandRepresentation> toModel(BrotBestandRepresentation brotBestand) {
        return EntityModel.of(brotBestand,
                linkTo(methodOn(BrotBestandController.class).one(brotBestand.getId())).withSelfRel(),
                linkTo(methodOn(BrotBestandController.class).all()).withRel("brotBestand"),
                linkTo(methodOn(BrotBestandController.class).newBrotBestand(brotBestand)).withSelfRel(),
                linkTo(methodOn(BrotBestandController.class).update(brotBestand, brotBestand.getId())).withSelfRel(),
                linkTo(methodOn(BrotBestandController.class).delete(brotBestand.getId())).withSelfRel());
    }
}