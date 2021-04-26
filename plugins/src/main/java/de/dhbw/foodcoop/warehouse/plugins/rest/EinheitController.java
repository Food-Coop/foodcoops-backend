package de.dhbw.foodcoop.warehouse.plugins.rest;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.EinheitToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToEinheitMapper;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitInUseException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.EinheitModelAssembler;
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
public class EinheitController {
    private final EinheitService service;
    private final EinheitModelAssembler assembler;
    private final EinheitToRepresentationMapper toRepresentationMapper;
    private final RepresentationToEinheitMapper toEinheitMapper;

    @Autowired
    public EinheitController(EinheitService service, EinheitModelAssembler assembler, EinheitToRepresentationMapper toRepresentationMapper, RepresentationToEinheitMapper toEinheitMapper) {
        this.service = service;
        this.assembler = assembler;
        this.toRepresentationMapper = toRepresentationMapper;
        this.toEinheitMapper = toEinheitMapper;
    }

    @GetMapping("/einheiten/{id}")
    public EntityModel<EinheitRepresentation> one(@PathVariable String id) {
        Einheit einheit = service.findById(id)
                .orElseThrow(() -> new EinheitNotFoundException(id));

        return assembler.toModel(toRepresentationMapper.apply(einheit));
    }

    @GetMapping("/einheiten")
    public CollectionModel<EntityModel<EinheitRepresentation>> all() {
        List<EntityModel<EinheitRepresentation>> einheits = service.all().stream()
                .map(toRepresentationMapper)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(einheits,
                linkTo(methodOn(EinheitController.class).all()).withSelfRel());
    }

    @PostMapping("/einheiten")
    public ResponseEntity<?> newEinheit(@RequestBody EinheitRepresentation newEinheit) {
        String id = newEinheit.getId() == null ||
                newEinheit.getId().isBlank() ||
                newEinheit.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newEinheit.getId();

        Einheit withId = new Einheit(id, newEinheit.getName());
        Einheit einheit = service.save(withId);
        EntityModel<EinheitRepresentation> entityModel = assembler.toModel(toRepresentationMapper.apply(einheit));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/einheiten/{id}")
    ResponseEntity<?> delete(@PathVariable String id) throws EinheitInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
