package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.repositories.EinheitRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EinheitRepositoryBridge implements EinheitRepository {
    private final SpringDataEinheitRepository springDataEinheitRepository;

    @Autowired
    public EinheitRepositoryBridge(SpringDataEinheitRepository springDataEinheitRepository) {
        this.springDataEinheitRepository = springDataEinheitRepository;
    }

    @Override
    public List<Einheit> alle() {
        return springDataEinheitRepository.findAll();
    }

    @Override
    public Einheit speichern(Einheit einheit) {
        return springDataEinheitRepository.save(einheit);
    }

    @Override
    public Optional<Einheit> findeMitId(String id) {
        return springDataEinheitRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataEinheitRepository.deleteById(id);
    }


}
