package de.dhbw.foodcoop.warehouse.plugins.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.BestellungToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.DeadlineToRepresentationMapper;
import de.dhbw.foodcoop.warehouse.adapters.representations.mappers.RepresentationToBestellungMapper;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestellungService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungInUseException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestellungNotFoundException;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.BrotBestellungModelAssembler;
import de.dhbw.foodcoop.warehouse.plugins.rest.assembler.DeadlineModelAssembler;

@RestController
public class BrotBestellungController {
    private final BrotBestellungService service;
    private final RepresentationToBestellungMapper toBrotBestellung;
    private final BestellungToRepresentationMapper toPresentation;
    private final BrotBestellungModelAssembler assembler;
    private final DeadlineService deadlineService;
    private final DeadlineToRepresentationMapper deadlineToPresentation;
    private final DeadlineModelAssembler deadlineAssembler;

    @Autowired
    public BrotBestellungController(BrotBestellungService service, RepresentationToBestellungMapper toBrotBestellung, BestellungToRepresentationMapper toPresentation, BrotBestellungModelAssembler assembler, DeadlineService deadlineService, DeadlineToRepresentationMapper deadlineToPresentation, DeadlineModelAssembler deadlineAssembler) {
        this.service = service;
        this.toBrotBestellung = toBrotBestellung;
        this.toPresentation = toPresentation;
        this.assembler = assembler;
        this.deadlineService = deadlineService;
        this.deadlineToPresentation = deadlineToPresentation;
        this.deadlineAssembler = deadlineAssembler;
    }

    @GetMapping("/brotBestellung/{id}")
    public EntityModel<BrotBestellungRepresentation> one(@PathVariable String id) {
        BrotBestellung brot = service.findById(id)
                .orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellungRepresentation presentation = (BrotBestellungRepresentation)toPresentation.apply(brot);
        return assembler.toModel(presentation);
    }

    @GetMapping("/brotBestellung")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> all() {
       	List<BrotBestellung> brotBestellungen = service.all();
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}

        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndPerson(@PathVariable String person_id){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampOfDeadLine(-1);
     	List<BrotBestellung> brotBestellungen = service.findByDateAfterAndPerson(datum, person_id);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
      
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/person/{person_id}")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateBetween(@PathVariable String person_id){
        Timestamp datum1 = getTimestampOfDeadLine(-1);
        Timestamp datum2 = getTimestampOfDeadLine(-2);
     	List<BrotBestellung> brotBestellungen = service.findByDateBetween(datum1, datum2, person_id);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
      
        return CollectionModel.of(brote,
                linkTo(methodOn(BrotBestellungController.class).all()).withSelfRel());
    }

    @GetMapping("/brotBestellung/datum/menge")
    public CollectionModel<EntityModel<BrotBestellungRepresentation>> findByDateAfterAndSum(){//@PathVariable Timestamp datum1, @PathVariable Timestamp datum2){
        //Timestamp datum1 = getTimestampNow();
        Timestamp datum = getTimestampOfDeadLine(-1);
     	List<BrotBestellung> brotBestellungen = service.findByDateAfterAndSum(datum);
    	List<EntityModel<BrotBestellungRepresentation>> brote = new ArrayList<>();
    	for(BrotBestellung b : brotBestellungen) {
    		BrotBestellungRepresentation br = (BrotBestellungRepresentation) toPresentation.apply(b);
    		EntityModel<BrotBestellungRepresentation> em = assembler.toModel(br);
    		brote.add(em);
    	}
     
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
        BrotBestellung brotBestellung = service.save((BrotBestellung)toBrotBestellung.apply(newBrotBestellung));
        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel((BrotBestellungRepresentation) toPresentation.apply(brotBestellung));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping ("/brotBestellung/{id}")
    public ResponseEntity<?> update(@RequestBody BrotBestellungRepresentation brotBestellung, @PathVariable String id) {

        BrotBestellung oldBrotBestellung = service.findById(id).orElseThrow(() -> new BrotBestellungNotFoundException(id));
        BrotBestellung updateBrotBestellung = (BrotBestellung) toBrotBestellung.update(oldBrotBestellung, brotBestellung);

        BrotBestellung saved = service.save(updateBrotBestellung);

        EntityModel<BrotBestellungRepresentation> entityModel = assembler.toModel((BrotBestellungRepresentation) toPresentation.apply(saved));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/brotBestellung/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws BrotBestellungInUseException {

        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    public Timestamp getTimestampOfDeadLine(int n) {
        //n = 0 => letzte Deadline, n = -1 => vorletzte Deadline, ..
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
        

        Calendar calendarNow = Calendar.getInstance();
        int yearNow = calendarNow.get(Calendar.YEAR);
        int monthNow = calendarNow.get(Calendar.MONTH);
        int dayNow = calendarNow.get(Calendar.DATE);
        int hourNow = calendarNow.get(Calendar.HOUR_OF_DAY);
        int minuteNow = calendarNow.get(Calendar.MINUTE);
        int secondNow = calendarNow.get(Calendar.SECOND);
        calendarNow.set(yearNow, monthNow, dayNow, hourNow, minuteNow, secondNow);
       
        Time timeNow = new Time(0L);
        timeNow.setTime(new java.util.Date().getTime());
        if(calendar.get(Calendar.WEEK_OF_YEAR) == calendarNow.get(Calendar.WEEK_OF_YEAR) && calendar.get(Calendar.DAY_OF_WEEK) <= calendarNow.get(Calendar.DAY_OF_WEEK) && lastDeadline.get(0).getContent().getTime().getHours() <= timeNow.getHours()){
               System.out.println(lastDeadline.get(0).getContent().getTime().getHours() + " + " + timeNow.getHours());
               System.out.println(lastDeadline.get(0).getContent().getTime().getMinutes() + " + " + timeNow.getMinutes());
                if ( calendar.get(Calendar.DAY_OF_WEEK) == calendarNow.get(Calendar.DAY_OF_WEEK) && lastDeadline.get(0).getContent().getTime().getHours() <= timeNow.getHours()){
                        if(lastDeadline.get(0).getContent().getTime().getHours() == timeNow.getHours() && lastDeadline.get(0).getContent().getTime().getMinutes() <= timeNow.getMinutes()){
                                if(lastDeadline.get(0).getContent().getTime().getMinutes() == timeNow.getMinutes() && lastDeadline.get(0).getContent().getTime().getSeconds() <= timeNow.getSeconds()){
                                        System.out.println("True + True + True");
                                        n += 1;
                                }else if ( lastDeadline.get(0).getContent().getTime().getMinutes() < timeNow.getMinutes() ) {
                                        n += 1;
                                }
                                else {
                                        System.out.println("True + True + False");
                                        //n += 1;
                                }
                        }
                        else if ( lastDeadline.get(0).getContent().getTime().getHours() < timeNow.getHours() ) {
                                n += 1;
                        }
                        else{
                                System.out.println("True + False + False");
                                //n += 1;
                        }
                }
                else{
                        System.out.println("False + False");
                        n += 1;
                }
        }
        else if ( calendar.get(Calendar.DAY_OF_WEEK) == calendarNow.get(Calendar.DAY_OF_WEEK) ){
        }
        else if ( calendar.get(Calendar.DAY_OF_WEEK) == 1 && calendarNow.get(Calendar.DAY_OF_WEEK) > 1 ){
        }
        else if ( calendar.get(Calendar.WEEK_OF_YEAR) == calendarNow.get(Calendar.WEEK_OF_YEAR) && calendar.get(Calendar.DAY_OF_WEEK) > calendarNow.get(Calendar.DAY_OF_WEEK) ){
        }
        else{
                n += 1;
        }
        if(calendarNow.get(Calendar.DAY_OF_WEEK) == 1) {
                if(calendar.get(Calendar.DAY_OF_WEEK) != 1 || lastDeadline.get(0).getContent().getTime().getHours() < timeNow.getHours()){
                        if(lastDeadline.get(0).getContent().getTime().getHours() == timeNow.getHours() && lastDeadline.get(0).getContent().getTime().getMinutes() <= timeNow.getMinutes()){
                                if(lastDeadline.get(0).getContent().getTime().getMinutes() == timeNow.getMinutes() && lastDeadline.get(0).getContent().getTime().getSeconds() <= timeNow.getSeconds()){
                                        System.out.println("True + True + True");
                                        n += 1;
                                }else if ( lastDeadline.get(0).getContent().getTime().getMinutes() < timeNow.getMinutes() ) {
                                        n += 1;
                                }
                                else {
                                        System.out.println("True + True + False");
                                        //n += 1;
                                }
                        }
                        else if ( lastDeadline.get(0).getContent().getTime().getHours() < timeNow.getHours() ) {
                                n += 1;
                        }
                        else{
                                System.out.println("True + False + False");
                                //n += 1;
                        }
                }
        }
        calendar.add(Calendar.WEEK_OF_MONTH, n);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        Time time = lastDeadline.get(0).getContent().getTime();
        calendar.set(year, month, day, time.getHours(), time.getMinutes(), time.getSeconds() );
        Date then = calendar.getTime();
        Timestamp datum = new Timestamp(then.getTime());
        System.out.println("Deadline: " + datum + " " + n);
        return datum;
    }
}

