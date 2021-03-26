package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.application.LagerService.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.KategorieModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.exceptions.KategorieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class KategorieController {
    private final KategorieService service;
    private final KategorieModelAssembler assembler;

    @Autowired
    public KategorieController(KategorieService service, KategorieModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/kategorie/{id}")
    public EntityModel<Kategorie> one(@PathVariable String id) {
        Kategorie kategorie = service.findById(id)
                .orElseThrow(() -> new KategorieNotFoundException(id));
        return assembler.toModel(kategorie);
    }

    @GetMapping("/kategorie")
    public CollectionModel<EntityModel<Kategorie>> all() {
        List<EntityModel<Kategorie>> kategories = service.all().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(kategories,
                linkTo(methodOn(KategorieController.class).all()).withSelfRel());
    }
}
