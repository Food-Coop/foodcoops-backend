package de.dhbw.foodcoop.warehouse.application.deadline;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.exceptions.DeadlineNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.repositories.DeadlineRepository;

@Service
public class DeadlineService {
    private final DeadlineRepository repository;
    
    @Autowired
    BestellÜbersichtService gbmService;

    @Autowired
    public DeadlineService(DeadlineRepository repository) {
        this.repository = repository;
    }

    public List<Deadline> all() {
        return repository.alle();
    }

    public Deadline last() {
        return repository.letzte().orElseThrow(() -> new DeadlineNotFoundException());
    }

    public Deadline save(Deadline deadline) {
        Deadline d = repository.speichern(deadline);
        gbmService.createList();
        return d;
    }

    public Optional<Deadline> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
    
    public Optional<Deadline> getByPosition(int position) {
    	return repository.findeNachReihenfolge(position);
    }
    
    public Deadline coldStart(Deadline deadline) {
    	return repository.speichern(deadline);
    }
    /**
     * 
     * Berechne vom gesetzten Tag + Uhrzeit das Datum für die nächste Deadline, abhängig vom aktuellen Timestamp
     * also: Montag, 23.3 es wird auf Donnerstag 12:00 gesetzt = > bekomme Donnerstag 27.3 12:00 als LocaleDateTime
     * 
     * Wenn aktuelles Datum > 27.3 12:00
     * 
     * Aktualisiere Deadline auf 27.3 12:00 + 7 Tage

     * 
     * 
     */
    
    public Optional<Deadline> updateDeadline() {
    	Optional<Deadline> optDead = repository.letzte();
    	if(optDead.isEmpty()) return Optional.empty();
    	Deadline d = optDead.get();
    	LocalDateTime dateForDeadline = calculateDateFromDeadline(d);
    	if(LocalDateTime.now().isAfter(dateForDeadline)) {

    		Deadline deadline = new Deadline(UUID.randomUUID().toString(), d.getWeekday(), d.getTime(), LocalDateTime.now());
    		return Optional.of(save(deadline));
    	}
		return Optional.empty();
    	
    }
    public static final Map<String, DayOfWeek> germanDaysOfWeek =
    	    Arrays.stream(DayOfWeek.values()).collect(
    	        Collectors.toMap(
    	            d -> d.getDisplayName(TextStyle.FULL, Locale.GERMAN), d -> d));
    
    public static final Map<DayOfWeek, String> germanDaysOfWeekReversed =
    	    Arrays.stream(DayOfWeek.values()).collect(
    	        Collectors.toMap(d -> d,
    	            d -> d.getDisplayName(TextStyle.FULL, Locale.GERMAN)));
    
    public LocalDateTime calculateDateFromDeadline(Deadline d) {
    	
    	
    	LocalDateTime date = d.getDatum(); // Der Timestamp an dem die deadline gesetzt wurde
    	LocalTime t = date.toLocalTime(); // die Zeit des timestamps
    	LocalTime target = LocalTime.of(d.getTime().getHours(), d.getTime().getMinutes(), d.getTime().getSeconds()); //Die Zeit des ziels
    	
    	if(germanDaysOfWeek.get(d.getWeekday()).getValue() == date.getDayOfWeek().getValue()) {
    		if(t.isBefore(target)) {
    			return LocalDateTime.of(date.toLocalDate(), target);
    		} else {
    			
    			return LocalDateTime.of(date.toLocalDate().plusDays(7), target);
    		}
    	}
    	return LocalDateTime.of(date.with(TemporalAdjusters.next(germanDaysOfWeek.get(d.getWeekday()))).toLocalDate(), target);
    
    }
}   
