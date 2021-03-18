package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LagerResourceService {
    private final KategorieRepository kategorieRepository;

    @Autowired
    public LagerResourceService(KategorieRepository kategorieRepository) {
        this.kategorieRepository = kategorieRepository;
    }

    public List<Kategorie> getAllKategories() {
        return kategorieRepository.alleKategorienAbrufen()
                .stream().sorted(Comparator.comparing(Kategorie::getName))
                .collect(Collectors.toList());
    }

    public void addKategorie(Kategorie kategorie) {
        kategorieRepository.speichern(kategorie);
    }
}
