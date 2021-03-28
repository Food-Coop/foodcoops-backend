package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.plugins.rest.ProduktController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProduktModelAssembler implements RepresentationModelAssembler<ProduktPresentation, EntityModel<ProduktPresentation>> {
    @Override
    public EntityModel<ProduktPresentation> toModel(ProduktPresentation produkt) {
        return EntityModel.of(produkt,
                linkTo(methodOn(ProduktController.class).one(produkt.getId())).withSelfRel(),
                linkTo(methodOn(ProduktController.class).all()).withRel("produkte"));
    }
}
