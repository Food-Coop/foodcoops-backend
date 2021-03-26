package de.dhbw.foodcoop.warehouse.plugins.rest.assembler;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.plugins.rest.KategorieController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class KategorieModelAssembler implements RepresentationModelAssembler<Kategorie, EntityModel<Kategorie>> {

    @Override
    public EntityModel<Kategorie> toModel(Kategorie kategorie) {
        return EntityModel.of(kategorie,
                linkTo(methodOn(KategorieController.class).one(kategorie.getId())).withSelfRel(),
                linkTo(methodOn(KategorieController.class).all()).withRel("kategorien"));
    }
}
