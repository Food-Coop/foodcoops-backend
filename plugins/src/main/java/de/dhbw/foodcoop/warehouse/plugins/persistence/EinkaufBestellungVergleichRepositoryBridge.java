package de.dhbw.foodcoop.warehouse.plugins.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufBestellungVergleichRepository;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Repository
public class EinkaufBestellungVergleichRepositoryBridge implements EinkaufBestellungVergleichRepository {
    private final SpringDataEinkaufBestellungVergleichRepository springDataEinkaufBestellungVergleichRepository;

    @Autowired
    public EinkaufBestellungVergleichRepositoryBridge(SpringDataEinkaufBestellungVergleichRepository springDataEinkaufBestellungVergleichRepository) {
        this.springDataEinkaufBestellungVergleichRepository = springDataEinkaufBestellungVergleichRepository;
    }
	@Override
	public List<EinkaufBestellungVergleich> alle() {
		// TODO Auto-generated method stub
		return springDataEinkaufBestellungVergleichRepository.findAll();
	}

	@Override
	public EinkaufBestellungVergleich speichern(EinkaufBestellungVergleich einkaufBestellungVergleich) {
		// TODO Auto-generated method stub
		return springDataEinkaufBestellungVergleichRepository.save(einkaufBestellungVergleich);
	}

	@Override
	public Optional<EinkaufBestellungVergleich> findeMitId(String id) {
		// TODO Auto-generated method stub
		return springDataEinkaufBestellungVergleichRepository.findById(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		springDataEinkaufBestellungVergleichRepository.deleteById(id);
	}
	

}
