package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.ProduktController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProduktModelAssembler implements RepresentationModelAssembler<ProduktRepresentation, EntityModel<ProduktRepresentation>> {
    @Override
    public EntityModel<ProduktRepresentation> toModel(ProduktRepresentation produkt) {
        return EntityModel.of(produkt,
                linkTo(methodOn(ProduktController.class).one(produkt.getId())).withSelfRel(),
                linkTo(methodOn(ProduktController.class).all()).withRel("produkte"),
                linkTo(methodOn(ProduktController.class).newProdukt(produkt)).withSelfRel(),
                linkTo(methodOn(ProduktController.class).update(produkt, produkt.getId())).withSelfRel(),
                linkTo(methodOn(ProduktController.class).delete(produkt.getId())).withSelfRel());
    }
}
