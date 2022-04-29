package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.BrotBestellungController;

@Component
public class BrotBestellungModelAssembler implements RepresentationModelAssembler<BrotBestellungRepresentation, EntityModel<BrotBestellungRepresentation>> {
    @Override
    public EntityModel<BrotBestellungRepresentation> toModel(BrotBestellungRepresentation brotBestellung) {
        return EntityModel.of(brotBestellung,
                linkTo(methodOn(BrotBestellungController.class).one(brotBestellung.getId())).withSelfRel(),
                linkTo(methodOn(BrotBestellungController.class).all()).withRel("brotBestellung"),
                linkTo(methodOn(BrotBestellungController.class).newBrotBestellung(brotBestellung)).withSelfRel(),
                linkTo(methodOn(BrotBestellungController.class).update(brotBestellung, brotBestellung.getId())).withSelfRel(),
                linkTo(methodOn(BrotBestellungController.class).delete(brotBestellung.getId())).withSelfRel());
    }
}
