package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.TooMuchBuyRepository;

@Repository
public class TooMuchBuyRepositoryBridge implements TooMuchBuyRepository{

	@Autowired
	SpringDataTooMuchBuyRepository springDataTooMuchBuyRepository;

	@Override
	public List<TooMuchBuyEntity> alle() {
		// TODO Auto-generated method stub
		return springDataTooMuchBuyRepository.findAll();
	}

	@Override
	public TooMuchBuyEntity speichern(TooMuchBuyEntity produkt) {
		// TODO Auto-generated method stub
		return springDataTooMuchBuyRepository.save(produkt);
	}

	@Override
	public Optional<TooMuchBuyEntity> findeMitId(String id) {
		// TODO Auto-generated method stub
		return springDataTooMuchBuyRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		springDataTooMuchBuyRepository.deleteById(id);
	}
}
