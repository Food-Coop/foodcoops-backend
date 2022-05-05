package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BrotBestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBrotBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.BrotBestellungModelAssembler;

@RestController
public class BrotBestellungController {
    private final BrotBestellungService service;
    private final RepresentationToBrotBestellungMapper toBrotBestellung;
    private final BrotBestellungToRepresentationMapper toPresentation;
    private final BrotBestellungModelAssembler assembler;

    @Autowired
    public BrotBestellungController(BrotBestellungService service, RepresentationToBrotBestellungMapper toBrotBestellung, BrotBestellungToRepresentationMapper toPresentation, BrotBestellungModelAssembler assembler) {
        this.service = service;
        this.toBrotBestellung = toBrotBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/brotBestellung/{id}")
    public EntityModel<BrotBestellungRepresentation> one(@PathVariable String id) {
        BrotBestellung brot = service.findById(id)
                .orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellungRepresentation presentation = toPresentation.apply(brot);
        return assembler.toModel(presentation);
    }

    @GetMapping("/brotBestellung")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> all() {
        List<EntityModel<BrotBestellungRepresentation>> brote = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndPerson(@PathVariable String person_id){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampOfDeadLine(-1);
        List<EntityModel<BrotBestellungRepresentation>> brote = service.findByDateAfterAndPerson(datum, person_id).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/person/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateBetween(@PathVariable String person_id){
        Timestamp datum1 = getTimestampOfDeadLine(-1);
        Timestamp datum2 = getTimestampOfDeadLine(-2);
        List<EntityModel<BrotBestellungRepresentation>> brote = service.findByDateBetween(datum1, datum2, person_id).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/menge")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampOfDeadLine(-1);
        List<EntityModel<BrotBestellungRepresentation>> brote = service.findByDateAfterAndSum(datum).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @PostMapping("/brotBestellung")
    public ResponseEntity<?> newBrotBestellung(@RequestBody BrotBestellungRepresentation newBrotBestellung) {
        String id = newBrotBestellung.getId() == null ||
                newBrotBestellung.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newBrotBestellung.getId();
        newBrotBestellung.setId(id);
        BrotBestellung brotBestellung = service.save(toBrotBestellung.apply(newBrotBestellung));
        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel(toPresentation.apply(brotBestellung));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/brotBestellung/{id}")
    public ResponseEntity<?> update(@RequestBody BrotBestellungRepresentation brotBestellung, @PathVariable String id) {

        BrotBestellung oldBrotBestellung = service.findById(id).orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellung updateBrotBestellung = toBrotBestellung.update(oldBrotBestellung, brotBestellung);

        BrotBestellung saved = service.save(updateBrotBestellung);

        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/brotBestellung/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BrotBestellungInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    private Timestamp getTimestampOfDeadLine(int n) {
        //n = -1 => letzte Deadline, n = -2 => vorletzte Deadline, ...
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar2.add(Calendar.WEEK_OF_MONTH, n);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DATE);
        calendar2.set(year, month, day, 0, 0, 0);
        Date then = calendar2.getTime();
        Timestamp datum2 = new Timestamp(then.getTime());
        return datum2;
    }

}

