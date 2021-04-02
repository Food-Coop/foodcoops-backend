package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduktService {
    private final ProduktRepository repository;

    @Autowired
    public ProduktService(ProduktRepository repository) {
        this.repository = repository;
    }

    public Optional<Produkt> findById(String id) {
        return repository.findeMitId(id);
    }

    public List<Produkt> all() {
        return repository.alle();
    }

    public Produkt save(Produkt newProdukt) {
        return repository.speichern(newProdukt);
    }
}
