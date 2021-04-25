package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.ProduktToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToProduktMapper;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.ProduktModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProduktController {
    private final ProduktService service;
    private final RepresentationToProduktMapper toProdukt;
    private final ProduktToRepresentationMapper toPresentation;
    private final ProduktModelAssembler assembler;

    @Autowired
    public ProduktController(ProduktService service, RepresentationToProduktMapper toProdukt, ProduktToRepresentationMapper toPresentation, ProduktModelAssembler assembler) {
        this.service = service;
        this.toProdukt = toProdukt;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/produkte/{id}")
    public EntityModel<ProduktRepresentation> one(@PathVariable String id) {
        Produkt produkt = service.findById(id)
                .orElseThrow(() -> new ProduktNotFoundException(id));
        ProduktRepresentation presentation = toPresentation.apply(produkt);
        return assembler.toModel(presentation);
    }

    @GetMapping("/produkte")
    public CollectionModel<EntityModel<ProduktRepresentation>> all() {
        List<EntityModel<ProduktRepresentation>> produkts = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(ProduktController.class).all()).withSelfRel());
    }

    @PostMapping("/produkte")
    ResponseEntity<?> newProdukt(@RequestBody ProduktRepresentation newProdukt) {
        String id = newProdukt.getId() == null ||
                newProdukt.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newProdukt.getId();
        newProdukt.setId(id);
        Produkt produkt = service.save(toProdukt.apply(newProdukt));
        EntityModel<ProduktRepresentation> entityModel = assembler.toModel(toPresentation.apply(produkt));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/produkte/{id}")
    ResponseEntity<?> update(@RequestBody ProduktRepresentation newProdukt, @PathVariable String id) {
        Produkt oldProdukt = service.findById(id).orElseThrow(() -> new ProduktNotFoundException(id));
        Produkt updateProdukt = toProdukt.update(oldProdukt, newProdukt);

        Produkt saved = service.save(updateProdukt);

        EntityModel<ProduktRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }


    @DeleteMapping("/produkte/{id}")
    ResponseEntity<?> delete(@PathVariable String id) throws ProduktInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
