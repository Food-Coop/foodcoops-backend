package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestandRepository;

@Repository
public class FrischBestandRepositoryBridge implements FrischBestandRepository{
    private final SpringDataFrischBestandRepository springDataFrischBestandRepository;

    @Autowired
    public FrischBestandRepositoryBridge(SpringDataFrischBestandRepository springDataFrischBestandRepository) {
        this.springDataFrischBestandRepository = springDataFrischBestandRepository;
    }

    @Override
    public List<FrischBestand> alle() {
        return springDataFrischBestandRepository.findAll();
    }

    @Override
    public FrischBestand speichern(FrischBestand FrischBestand) {
        return springDataFrischBestandRepository.save(FrischBestand);
    }

    @Override
    public Optional<FrischBestand> findeMitId(String id) {
        return springDataFrischBestandRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataFrischBestandRepository.deleteById(id);
    }
}
