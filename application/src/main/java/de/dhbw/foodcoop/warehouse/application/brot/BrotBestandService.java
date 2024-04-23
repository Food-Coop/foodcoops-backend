package de.dhbw.foodcoop.warehouse.application.brot;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestandRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrotBestandService {
    private final BrotBestandRepository repository;
    
    @Autowired
    public BrotBestandService(BrotBestandRepository repository) {
        this.repository = repository;
    }

    public Optional<BrotBestand> findById(String id) {
        return repository.findeMitId(id);
    }

    public List<BrotBestand> all() {
        return repository.alle();
    }

    public BrotBestand save(BrotBestand newBrotBestand) {
        return repository.speichern(newBrotBestand);
    }

    
    public void deleteById(String id) {
    	Optional<BrotBestand> toBeDeleted = repository.findeMitId(id);
        if (toBeDeleted.isEmpty()) {
            return;
        }
        repository.deleteById(id);
    }
    
    public List<BrotBestand> allOrdered() {
    	return repository.alleSortiert();
    }
}
