package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.plugins.rest.KategorieController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class KategorieModelAssembler implements RepresentationModelAssembler<KategorieRepresentation, EntityModel<KategorieRepresentation>> {

    @Override
    public EntityModel<KategorieRepresentation> toModel(KategorieRepresentation kategorie) {
        return EntityModel.of(kategorie,
                linkTo(methodOn(KategorieController.class).one(kategorie.getId())).withSelfRel(),
                linkTo(methodOn(KategorieController.class).all()).withRel("kategorien"),
                linkTo(methodOn(KategorieController.class).newKategorie(kategorie)).withRel("post"),
                linkTo(methodOn(KategorieController.class).update(kategorie, kategorie.getId())).withRel("update"),
                linkTo(methodOn(KategorieController.class).delete(kategorie.getId())).withRel("delete"));
    }
}
