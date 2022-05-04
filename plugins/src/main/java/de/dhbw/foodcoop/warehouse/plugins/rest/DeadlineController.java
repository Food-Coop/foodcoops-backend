package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToDeadlineMapper;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.exceptions.DeadlineInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.DeadlineNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;

@RestController
public class DeadlineController {
    private final DeadlineService service;
    private final RepresentationToDeadlineMapper toDeadline;
    private final DeadlineToRepresentationMapper toPresentation;
    private final DeadlineModelAssembler assembler;

    @Autowired
    public DeadlineController(DeadlineService service, RepresentationToDeadlineMapper toDeadline, DeadlineToRepresentationMapper toPresentation, DeadlineModelAssembler assembler) {
        this.service = service;
        this.toDeadline = toDeadline;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/deadline/{id}")
    public EntityModel<DeadlineRepresentation> one(@PathVariable String id) {
        Deadline deadline = service.findById(id)
                .orElseThrow(() -> new DeadlineNotFoundException(id));
        DeadlineRepresentation presentation = toPresentation.apply(deadline);
        return assembler.toModel(presentation);
    }

    @GetMapping("/deadline")
    public CollectionModel<EntityModel<DeadlineRepresentation>> all() {
        List<EntityModel<DeadlineRepresentation>> deadlines = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(deadlines,
                linkTo(methodOn(DeadlineController.class).all()).withSelfRel());
    }

    @GetMapping("/deadline/last")
    public CollectionModel<EntityModel<DeadlineRepresentation>> last() {
        List<EntityModel<DeadlineRepresentation>> deadlines = service.last().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        List<EntityModel<DeadlineRepresentation>> lastDeadline = deadlines.subList(deadlines.size()-1, deadlines.size());
        return CollectionModel.of(lastDeadline,
                linkTo(methodOn(DeadlineController.class).all()).withSelfRel());
    }

    @PostMapping("/deadline")
    public ResponseEntity<?> newDeadline(@RequestBody DeadlineRepresentation newDeadline) {
        String id = newDeadline.getId() == null ||
                newDeadline.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newDeadline.getId();
            newDeadline.setId(id);

        //Timestamp machen
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hour, minute, second);
        Date then = calendar.getTime();
        Timestamp datum = new Timestamp(then.getTime());
        newDeadline.setDatum(datum);

        Deadline deadline = service.save(toDeadline.apply(newDeadline));
        EntityModel<DeadlineRepresentation> entityModel = assembler.toModel(toPresentation.apply(deadline));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/deadline/{id}")
    public ResponseEntity<?> update(@RequestBody DeadlineRepresentation deadline, @PathVariable String id) {
        Deadline oldDeadline = service.findById(id).orElseThrow(() -> new DeadlineNotFoundException(id));
        Deadline updateDeadline = toDeadline.update(oldDeadline, deadline);

        Deadline saved = service.save(updateDeadline);

        EntityModel<DeadlineRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/deadline/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws DeadlineInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

