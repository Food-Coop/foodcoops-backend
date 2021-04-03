package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.repositories.EinheitRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EinheitService {
    private final EinheitRepository repository;

    @Autowired
    public EinheitService(EinheitRepository repository) {
        this.repository = repository;
    }

    public List<Einheit> all() {
        return repository.alle();
    }

    public Optional<Einheit> findById(String id) {
        return repository.findeMitId(id);
    }

    public Einheit save(Einheit newEinheit) {
        List<Einheit> all = all();
        List<Einheit> einheits = all.stream()
                .filter(e -> e.getName().equals(newEinheit.getName()))
                .collect(Collectors.toList());
        if (!einheits.isEmpty()) {
            return einheits.get(0);
        }
        return repository.speichern(newEinheit);
    }
}
