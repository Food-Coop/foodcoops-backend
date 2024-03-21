package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandBuyRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

@Component
public class RepresentationToEinkaufMapper implements Function<EinkaufRepresentation, EinkaufEntity> {
    @Autowired
    RepresentationToBestellungMapper bestellungMapper;
    
    @Autowired
    RepresentationToBestandBuyMapper bestandBuyMapper;





	@Override
	public EinkaufEntity apply(EinkaufRepresentation e) {
		// TODO Auto-generated method stub
		return new EinkaufEntity(e.getId(),
				e.getPersonId(),
				convertBestellungToList(e.getBestellungsEinkauf()),
				convertBestandToList(e.getBestandEinkauf()),
				e.getDate(),
				e.getBreadPriceAtTime(),
				e.getFreshPriceAtTime(),
				e.getBestandPriceAtTime());
		
	}
	
	private List<BestellungEntity> convertBestellungToList(Set<BestellungRepresentation> set) {
		if(set == null) {
			return null;
		}
		List<BestellungEntity> bestellung = new ArrayList<>();
		for(BestellungRepresentation b : set) {
			bestellung.add(bestellungMapper.apply(b));
		}
		return bestellung;
		
	}
	
	private List<BestandBuyEntity> convertBestandToList(Set<BestandBuyRepresentation> set) {
		if(set == null) {
			return null;
		}
		List<BestandBuyEntity> bestellung = new ArrayList<>();
		for(BestandBuyRepresentation b : set) {
			bestellung.add(bestandBuyMapper.apply(b));
		}
		return bestellung;
		
	}
}
