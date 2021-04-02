package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.application.LagerService.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.repositories.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.EinheitModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EinheitController {
    private final EinheitService service;
    private final EinheitModelAssembler assembler;

    @Autowired
    public EinheitController(EinheitService service, EinheitModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/einheit/{id}")
    public EntityModel<Einheit> one(String id) {
        Einheit einheit = service.findById(id)
                .orElseThrow(() -> new EinheitNotFoundException(id));
        return assembler.toModel(einheit);
    }

    @GetMapping("/einheit")
    public CollectionModel<EntityModel<Einheit>> all() {
        List<EntityModel<Einheit>> einheits = service.all().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(einheits,
                linkTo(methodOn(EinheitController.class).all()).withSelfRel());
    }

    @PostMapping("/einheit")
    ResponseEntity<?> newEinheit(@RequestBody Einheit newEinheit) {
        Einheit einheit = service.save(newEinheit);
        EntityModel<Einheit> entityModel = assembler.toModel(einheit);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
