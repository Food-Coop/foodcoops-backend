package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.DeadlineController;

@Component
public class DeadlineModelAssembler implements RepresentationModelAssembler<DeadlineRepresentation, EntityModel<DeadlineRepresentation>> {
    @Override
    public EntityModel<DeadlineRepresentation> toModel(DeadlineRepresentation deadline) {
        return EntityModel.of(deadline,
                linkTo(methodOn(DeadlineController.class).one(deadline.getId())).withSelfRel(),
                linkTo(methodOn(DeadlineController.class).all()).withRel("deadline"),
                linkTo(methodOn(DeadlineController.class).newDeadline(deadline)).withSelfRel(),
                linkTo(methodOn(DeadlineController.class).update(deadline, deadline.getId())).withSelfRel(),
                linkTo(methodOn(DeadlineController.class).delete(deadline.getId())).withSelfRel());
    }
}
