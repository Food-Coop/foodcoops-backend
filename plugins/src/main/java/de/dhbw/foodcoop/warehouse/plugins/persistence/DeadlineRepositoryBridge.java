package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.repositories.DeadlineRepository;

@Repository
public class DeadlineRepositoryBridge implements DeadlineRepository{
    private final SpringDataDeadlineRepository springDataDeadlineRepository;

    @Autowired
    public DeadlineRepositoryBridge(SpringDataDeadlineRepository springDataDeadlineRepository) {
        this.springDataDeadlineRepository = springDataDeadlineRepository;
    }

    @Override
    public List<Deadline> alle() {
        return springDataDeadlineRepository.findAll();
    }

    @Override
    public Optional<Deadline> letzte() {
        return springDataDeadlineRepository.findLast();
    }

    @Override
    public Deadline speichern(Deadline deadline) {
        return springDataDeadlineRepository.save(deadline);
    }

    @Override
    public Optional<Deadline> findeMitId(String id) {
        return springDataDeadlineRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataDeadlineRepository.deleteById(id);
    }

	@Override
	public Optional<Deadline> findeNachReihenfolge(int position) {
		// TODO Auto-generated method stub
		return springDataDeadlineRepository.findFromSortedPosition(position);
	}
}
