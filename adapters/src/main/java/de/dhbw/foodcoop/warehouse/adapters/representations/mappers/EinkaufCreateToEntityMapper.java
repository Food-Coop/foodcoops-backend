package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandBuyRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungBuyRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufCreateRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

@Component
public class EinkaufCreateToEntityMapper implements Function<EinkaufCreateRepresentation, EinkaufEntity> {
   
    @Autowired
    RepresentationToBestellungBuyMapper bestellungBuyMapper;
    
    @Autowired
    RepresentationToBestandBuyMapper bestandBuyMapper;



	@Override
	public EinkaufEntity apply(EinkaufCreateRepresentation e) {
		// TODO Auto-generated method stub
		return new EinkaufEntity(e.getId(),
				e.getPersonId(),
				convertBestellungToList(e.getBestellungsEinkauf()),
				convertBestandToList(e.getBestandEinkauf()),
				null, 0, 0, 0);
	}
	
	private List<BestellungBuyEntity> convertBestellungToList(Set<BestellungBuyRepresentation> set) {
		if(set == null) {
			return null;
		}
		List<BestellungBuyEntity> bestellung = new ArrayList<>();
		for(BestellungBuyRepresentation b : set) {
			bestellung.add(bestellungBuyMapper.apply(b));
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
