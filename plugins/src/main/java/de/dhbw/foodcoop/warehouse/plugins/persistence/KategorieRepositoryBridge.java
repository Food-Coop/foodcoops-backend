package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KategorieRepositoryBridge implements KategorieRepository {
    private final SpringDataKategorieRepository springDataKategorieRepository;

    @Autowired
    public KategorieRepositoryBridge(SpringDataKategorieRepository springDataKategorieRepository) {
        this.springDataKategorieRepository = springDataKategorieRepository;
    }

    @Override
    public List<Kategorie> alle() {
        return springDataKategorieRepository.findAll();
    }

    @Override
    public Kategorie speichern(Kategorie kategorie) {
        return springDataKategorieRepository.save(kategorie);
    }

    @Override
    public Optional<Kategorie> findeMitId(String id) {
        return springDataKategorieRepository.findById(id);
    }
}
