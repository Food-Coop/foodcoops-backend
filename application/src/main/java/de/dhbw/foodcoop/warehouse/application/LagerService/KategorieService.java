package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KategorieService {
    private final KategorieRepository repository;

    @Autowired
    public KategorieService(KategorieRepository repository) {
        this.repository = repository;
    }

    public List<Kategorie> all() {
        return repository.alle()
                .stream().sorted(Comparator.comparing(Kategorie::getName))
                .collect(Collectors.toList());
    }

    public Kategorie save(Kategorie kategorie) {
        return repository.speichern(kategorie);
    }

    public Optional<Kategorie> findById(String id) {
        return repository.findeMitId(id);
    }

}
