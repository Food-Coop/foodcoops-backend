package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.KategorieToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToKategorieMapper;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.KategorieModelAssembler;
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
public class KategorieController {
    private final KategorieService service;
    private final KategorieToRepresentationMapper toRepresentation;
    private final RepresentationToKategorieMapper toKategorie;
    private final KategorieModelAssembler assembler;

    @Autowired
    public KategorieController(KategorieService service, KategorieToRepresentationMapper toRepresentation, RepresentationToKategorieMapper toKategorie, KategorieModelAssembler assembler) {
        this.service = service;
        this.toRepresentation = toRepresentation;
        this.toKategorie = toKategorie;
        this.assembler = assembler;
    }

    @GetMapping("/kategorien/{id}")
    public EntityModel<KategorieRepresentation> one(@PathVariable String id) {
        Kategorie kategorie = service.findById(id)
                .orElseThrow(() -> new KategorieNotFoundException(id));
        return assembler.toModel(toRepresentation.apply(kategorie));
    }

    @GetMapping("/kategorien")
    public CollectionModel<EntityModel<KategorieRepresentation>> all() {
        List<EntityModel<KategorieRepresentation>> kategories = service.all().stream()
                .map(toRepresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(kategories,
                linkTo(methodOn(KategorieController.class).all()).withSelfRel());
    }

    @PostMapping("/kategorien")
    ResponseEntity<?> newKategorie(@RequestBody KategorieRepresentation newKategorie) {
        String id = newKategorie.getId() == null ||
                newKategorie.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newKategorie.getId();
        newKategorie.setId(id);
        Kategorie saved = service.save(toKategorie.apply(newKategorie));
        EntityModel<KategorieRepresentation> entityModel = assembler.toModel(toRepresentation.apply(saved));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/kategorien/{id}")
    ResponseEntity<?> update(@RequestBody KategorieRepresentation inKategorie, @PathVariable String id) {

        Kategorie oldKategorie = service.findById(id).orElseThrow(() -> new KategorieNotFoundException(id));
        Kategorie newKategorie = toKategorie.apply(inKategorie);

        Kategorie saved = service.update(oldKategorie, newKategorie);

        EntityModel<KategorieRepresentation> entityModel = assembler.toModel(toRepresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/kategorien/{id}")
    ResponseEntity<?> delete(@PathVariable String id)  {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
