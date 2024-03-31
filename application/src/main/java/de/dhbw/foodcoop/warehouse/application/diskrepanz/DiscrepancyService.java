package de.dhbw.foodcoop.warehouse.application.diskrepanz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.DiscrepancyRepository;

@Service
public class DiscrepancyService {

    private final DiscrepancyRepository discrepancy;
    
    

    @Autowired
    public DiscrepancyService(DiscrepancyRepository discrepancy) {
        this.discrepancy = discrepancy;
    }
    
    public List<DiscrepancyEntity> findAll() {
    	return discrepancy.alle();
    }
    
    public void deleteById(String id) {
    	discrepancy.deleteById(id);
    }
    
    public DiscrepancyEntity findById(String id) {
    	return discrepancy.findeMitId(id).orElseThrow();
    }
    
    public DiscrepancyEntity save(DiscrepancyEntity entity) {
    	return discrepancy.speichern(entity);
    }
    
   
}
