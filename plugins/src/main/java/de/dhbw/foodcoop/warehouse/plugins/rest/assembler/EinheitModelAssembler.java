package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.plugins.rest.EinheitController;
import de.dhbw.foodcoop.warehouse.plugins.rest.ProduktController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EinheitModelAssembler implements RepresentationModelAssembler<Einheit, EntityModel<Einheit>> {
    @Override
    public EntityModel<Einheit> toModel(Einheit einheit) {
        return EntityModel.of(einheit,
                linkTo(methodOn(EinheitController.class).one(einheit.getId())).withSelfRel(),
                linkTo(methodOn(EinheitController.class).all()).withRel("Einheiten"));
    }
}
