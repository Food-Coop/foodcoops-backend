package de.dhbw.foodcoop.warehouse.application.frischbestellung;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;

@Service
public class FrischBestellungService {
    private final FrischBestellungRepository repository;

    @Autowired
    public FrischBestellungService(FrischBestellungRepository repository) {
        this.repository = repository;
    }

    public List<FrischBestellung> all() {
        return repository.alle();
    }

    public FrischBestellung save(FrischBestellung bestellung) {
        return repository.speichern(bestellung);
    }

    public Optional<FrischBestellung> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}   
