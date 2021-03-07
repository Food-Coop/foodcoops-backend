package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProduktRowRepository implements ProduktRepository {

    private final SpringDataProduktRowRepository springDataProduktRowRepository;


    @Autowired
    public ProduktRowRepository(SpringDataProduktRowRepository springDataProduktRowRepository) {
        this.springDataProduktRowRepository = springDataProduktRowRepository;
    }

    @Override
    public Produkt produktAnlegen(Produkt produkt) {
        return null;
    }

    @Override
    public Produkt produktAktualisieren(Produkt produkt) {
        return null;
    }

    @Override
    public Produkt produktLoeschen(Produkt produkt) {
        return null;
    }
}
