package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.presentations.ProduktPresentation;
import de.dhbw.foodcoop.warehouse.adapters.presentations.mappers.PresentationToProduktMapper;
import de.dhbw.foodcoop.warehouse.adapters.presentations.mappers.ProduktToPresentationMapper;
import de.dhbw.foodcoop.warehouse.application.LagerService.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.ProduktModelAssembler;
import de.dhbw.foodcoop.warehouse.domain.repositories.exceptions.ProduktNotFoundException;
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
    private final PresentationToProduktMapper toProdukt;
    private final ProduktToPresentationMapper toPresentation;
    private final ProduktModelAssembler assembler;

    @Autowired
    public ProduktController(ProduktService service, PresentationToProduktMapper toProdukt, ProduktToPresentationMapper toPresentation, ProduktModelAssembler assembler) {
        this.service = service;
        this.toProdukt = toProdukt;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/produkt/{id}")
    public EntityModel<ProduktPresentation> one(@PathVariable String id) {
        Produkt produkt = service.findById(id)
                .orElseThrow(() -> new ProduktNotFoundException(id));
        ProduktPresentation presentation = toPresentation.apply(produkt);
        return assembler.toModel(presentation);
    }

    @GetMapping("/produkt")
    public CollectionModel<EntityModel<ProduktPresentation>> all() {
        List<EntityModel<ProduktPresentation>> produkts = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/produkt")
    ResponseEntity<?> newProdukt(@RequestBody ProduktPresentation newProdukt) {
        Produkt produkt = service.create(toProdukt.apply(newProdukt));
        EntityModel<ProduktPresentation> entityModel = assembler.toModel(toPresentation.apply(produkt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
