package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.EinheitController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EinheitModelAssembler implements RepresentationModelAssembler<EinheitRepresentation, EntityModel<EinheitRepresentation>> {
    @Override
    public EntityModel<EinheitRepresentation> toModel(EinheitRepresentation einheit) {
        return EntityModel.of(einheit,
                linkTo(methodOn(EinheitController.class).one(einheit.getId())).withSelfRel(),
                linkTo(methodOn(EinheitController.class).all()).withRel("Einheiten"),
                linkTo(methodOn(EinheitController.class).newEinheit(einheit)).withRel("New Einheit"),
                linkTo(methodOn(EinheitController.class).delete(einheit.getId())).withRel("Delete"));
    }
}
