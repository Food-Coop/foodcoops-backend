package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.FrischBestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToFrischBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.FrischBestellungModelAssembler;

@RestController
public class FrischBestellungController {
    private final FrischBestellungService service;
    private final RepresentationToFrischBestellungMapper toFrischBestellung;
    private final FrischBestellungToRepresentationMapper toPresentation;
    private final FrischBestellungModelAssembler assembler;
    private final DeadlineService deadlineService;
    
    private final DeadlineToRepresentationMapper deadlineToPresentation;
    private final DeadlineModelAssembler deadlineAssembler;

    @Autowired
    public FrischBestellungController(FrischBestellungService service, RepresentationToFrischBestellungMapper toFrischBestellung, FrischBestellungToRepresentationMapper toPresentation, FrischBestellungModelAssembler assembler, DeadlineService deadlineService, DeadlineToRepresentationMapper deadlineToPresentation, DeadlineModelAssembler deadlineAssembler) {
        this.service = service;
        this.toFrischBestellung = toFrischBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
        this.deadlineService = deadlineService;
        this.deadlineToPresentation = deadlineToPresentation;
        this.deadlineAssembler = deadlineAssembler;
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
        Timestamp datum = getTimestampOfDeadLine(0);
        List<EntityModel<FrischBestellungRepresentation>> produkts = service.findByDateAfterAndPerson(datum, person_id).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/person/{person_id}")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateBetween(@PathVariable String person_id){
        Timestamp datum1 = getTimestampOfDeadLine(0);
        Timestamp datum2 = getTimestampOfDeadLine(-1);
        List<EntityModel<FrischBestellungRepresentation>> produkts = service.findByDateBetween(datum1, datum2, person_id).stream()
                .map(toPresentation)
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(produkts,
                linkTo(methodOn(FrischBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/frischBestellung/datum/menge")
    public CollectionModel<EntityModel<FrischBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampOfDeadLine(0);
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

    
    public Timestamp getTimestampOfDeadLine(int n) {
        //n = -1 => letzte Deadline, n = -2 => vorletzte Deadline, ..
        List<EntityModel<DeadlineRepresentation>> deadlines = deadlineService.last().stream()
                .map(deadlineToPresentation)
                .map(deadlineAssembler::toModel)
                .collect(Collectors.toList());
        List<EntityModel<DeadlineRepresentation>> lastDeadline = deadlines.subList(deadlines.size()-1, deadlines.size());
        Calendar calendar = Calendar.getInstance();
        switch(lastDeadline.get(0).getContent().getWeekday()){
                case "Montag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                        break;
                case "Dienstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                        break;
                case "Mittwoch":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                        break;
                case "Donnerstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                        break;
                case "Freitag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                        break;
                case "Samstag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                        break;
                case "Sonntag":
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                        break;
        }
        calendar.add(Calendar.WEEK_OF_MONTH, n);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        Time time = lastDeadline.get(0).getContent().getTime();
        calendar.set(year, month, day, time.getHours(), time.getMinutes(), time.getSeconds() );
        Date then = calendar.getTime();
        Timestamp datum = new Timestamp(then.getTime());
        System.out.println(datum);
        return datum;
    }

}

