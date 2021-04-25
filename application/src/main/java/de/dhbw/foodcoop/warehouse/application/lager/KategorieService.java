package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.exceptions.KategorieInUseException;
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

    public void deleteById(String id) {
        Optional<Kategorie> toBeDeleted = repository.findeMitId(id);
        if (toBeDeleted.isEmpty()) {
            return;
        }
        if (!toBeDeleted.get().getProdukte().isEmpty()) {
            throw new KategorieInUseException(id);
        }
        repository.deleteById(id);
    }

    public Kategorie update(Kategorie oldKategorie, Kategorie newKategorie) {
        Kategorie updateKategorie = new Kategorie(oldKategorie.getId()
                , newValueOrOld(oldKategorie.getName(), newKategorie.getName())
                , newValueOrOld(oldKategorie.getIcon(), newKategorie.getIcon())
                ,oldKategorie.getProdukte());
        return save(updateKategorie);
    }

    private String newValueOrOld(String old, String pototentialNew) {
        return pototentialNew.equals("undefined") ? old : pototentialNew;
    }
}
