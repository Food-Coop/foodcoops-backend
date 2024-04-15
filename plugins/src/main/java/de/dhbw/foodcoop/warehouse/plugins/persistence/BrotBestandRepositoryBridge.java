package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestandRepository;

@Repository
public class BrotBestandRepositoryBridge implements BrotBestandRepository{
    private final SpringDataBrotBestandRepository springDataBrotBestandRepository;

    @Autowired
    public BrotBestandRepositoryBridge(SpringDataBrotBestandRepository springDataBrotBestandRepository) {
        this.springDataBrotBestandRepository = springDataBrotBestandRepository;
    }

    @Override
    public List<BrotBestand> alle() {
        return springDataBrotBestandRepository.findAll();
    }

    @Override
    public BrotBestand speichern(BrotBestand brotBestand) {
        return springDataBrotBestandRepository.save(brotBestand);
    }

    @Override
    public Optional<BrotBestand> findeMitId(String id) {
        return springDataBrotBestandRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        springDataBrotBestandRepository.deleteById(id);
    }

	@Override
	public List<BrotBestand> alleSortiert() {
		// TODO Auto-generated method stub
		return springDataBrotBestandRepository.findAllByOrderByIdAsc();
	}
}
