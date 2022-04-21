package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.FrischBestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToFrischBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestellungModelAssembler;

@RestController
public class FrischBestellungController {
    private final FrischBestellungService service;
    private final RepresentationToFrischBestellungMapper toFrischBestellung;
    private final FrischBestellungToRepresentationMapper toPresentation;
    private final FrischBestellungModelAssembler assembler;

    @Autowired
    public FrischBestellungController(FrischBestellungService service, RepresentationToFrischBestellungMapper toFrischBestellung, FrischBestellungToRepresentationMapper toPresentation, FrischBestellungModelAssembler assembler) {
        this.service = service;
        this.toFrischBestellung = toFrischBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
    }

    @GetMapping("/frischBestellung/{id}")
    public EntityModel<FrischBestellungRepresentation> one(@PathVariable String id) {
        FrischBestellung produkt = service.findById(id)
                .orElseThrow(() -> new FrischBestellungNotFoundException(id));
        FrischBestellungRepresentation presentation = toPresentation.apply(produkt);
        return assembler.toModel(presentation);
    }

    @GetMapping("/frischBestellung")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> all() {
        List<EntityModel<FrischBestellungRepresentation>> produkts = service.all().stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/datum/{person_id}")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateAfterAndPerson(@PathVariable String person_id){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampLastDeadLine();
        List<EntityModel<FrischBestellungRepresentation>> produkts = service.findByDateAfterAndPerson(datum, person_id).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/datum/menge")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampLastDeadLine();
        List<EntityModel<FrischBestellungRepresentation>> produkts = service.findByDateAfterAndSum(datum).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @PostMapping("/frischBestellung")
    public ResponseEntity<?> newFrischBestellung(@RequestBody FrischBestellungRepresentation newFrischBestellung) {
        String id = newFrischBestellung.getId() == null ||
                newFrischBestellung.getId().equals("undefined") ?
                UUID.randomUUID().toString() :
                newFrischBestellung.getId();
        newFrischBestellung.setId(id);
        FrischBestellung frischBestellung = service.save(toFrischBestellung.apply(newFrischBestellung));
        EntityModel<FrischBestellungRepresentation> entityModel = assembler.toModel(toPresentation.apply(frischBestellung));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/frischBestellung/{id}")
    public ResponseEntity<?> update(@RequestBody FrischBestellungRepresentation changedFrischBestellung, @PathVariable String id) {
        
        System.out.println(id);

        FrischBestellung oldFrischBestellung = service.findById(id).orElseThrow(() -> new FrischBestellungNotFoundException(id));
        FrischBestellung updateFrischBestellung = toFrischBestellung.update(oldFrischBestellung, changedFrischBestellung);

        FrischBestellung saved = service.save(updateFrischBestellung);

        EntityModel<FrischBestellungRepresentation> entityModel = assembler.toModel(toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/frischBestellung/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws FrischBestellungInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    private Timestamp getTimestampLastDeadLine() {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar2.add(Calendar.WEEK_OF_MONTH, -1);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH);
        int day = calendar2.get(Calendar.DATE);
        calendar2.set(year, month, day, 0, 0, 0);
        Date then = calendar2.getTime();
        Timestamp datum2 = new Timestamp(then.getTime());
        return datum2;
    }

}

