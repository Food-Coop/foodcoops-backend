package de.dhbw.foodcoop.warehouse.application.deadline;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.repositories.DeadlineRepository;

@Service
public class DeadlineService {
    private final DeadlineRepository repository;

    @Autowired
    public DeadlineService(DeadlineRepository repository) {
        this.repository = repository;
    }

    public List<Deadline> all() {
        return repository.alle();
    }

    public Deadline save(Deadline deadline) {
        return repository.speichern(deadline);
    }

    public Optional<Deadline> findById(String id) {
        return repository.findeMitId(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}   
