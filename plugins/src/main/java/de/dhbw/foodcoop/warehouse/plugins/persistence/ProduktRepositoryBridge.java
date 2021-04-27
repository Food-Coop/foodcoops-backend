package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProduktRepositoryBridge implements ProduktRepository {

    private final SpringDataProduktRepository springDataProduktRepository;

    @Autowired
    public ProduktRepositoryBridge(SpringDataProduktRepository springDataProduktRepository) {
        this.springDataProduktRepository = springDataProduktRepository;
    }

    @Override
    public List<Produkt> alle() {
        return springDataProduktRepository.findAll();
    }

    @Override
    public Produkt speichern(Produkt produkt) {
        return springDataProduktRepository.save(produkt);
    }

    @Override
    public Optional<Produkt> findeMitId(String id) {
        return springDataProduktRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataProduktRepository.deleteById(id);
    }
}
