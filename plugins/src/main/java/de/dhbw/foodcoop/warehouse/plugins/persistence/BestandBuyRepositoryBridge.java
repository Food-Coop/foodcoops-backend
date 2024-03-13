package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.BestandBuyRepository;

@Repository
public class BestandBuyRepositoryBridge implements BestandBuyRepository{

	@Autowired
	SpringDataBestandBuyRepository springDataBestandBuyRepository;
	
	@Override
	public List<BestandBuyEntity> alle() {
		// TODO Auto-generated method stub
		return springDataBestandBuyRepository.findAll();
	}

	@Override
	public BestandBuyEntity speichern(BestandBuyEntity produkt) {
		// TODO Auto-generated method stub
		return springDataBestandBuyRepository.save(produkt);
	}

	@Override
	public Optional<BestandBuyEntity> findeMitId(String id) {
		// TODO Auto-generated method stub
		return springDataBestandBuyRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stu
		springDataBestandBuyRepository.deleteById(id);
		
	}

}
