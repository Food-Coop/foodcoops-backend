package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.application.LagerService.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.ProduktModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.exceptions.ProduktNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProduktController {
    private final ProduktService service;
    private final ProduktModelAssembler assembler;

    @Autowired
    public ProduktController(ProduktService service, ProduktModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/produkt/{id}")
    public EntityModel<Produkt> one(@PathVariable String id) {
        Produkt produkt = service.findById(id)
                .orElseThrow(() -> new ProduktNotFoundException(id));
        return assembler.toModel(produkt);
    }

    @GetMapping("/produkt")
    public CollectionModel<EntityModel<Produkt>> all() {
        List<EntityModel<Produkt>> produkts = service.all().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/produkt")
    ResponseEntity<?> newProdukt(@RequestBody Produkt newProdukt) {
        EntityModel<Produkt> entityModel = assembler.toModel(service.create(newProdukt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
