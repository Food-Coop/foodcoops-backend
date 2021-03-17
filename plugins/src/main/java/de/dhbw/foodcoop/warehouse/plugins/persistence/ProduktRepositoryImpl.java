package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProduktRepositoryImpl implements ProduktRepository {

    private final SpringDataProduktRepository springDataProduktRepository;

    @Autowired
    public ProduktRepositoryImpl(SpringDataProduktRepository springDataProduktRepository) {
        this.springDataProduktRepository = springDataProduktRepository;
    }

    @Override
    public List<Produkt> alleProdukteAbrufen() {
        return springDataProduktRepository.findAll();
    }

    @Override
    public Produkt speichern(Produkt produkt) {
        return springDataProduktRepository.save(produkt);
    }
}
