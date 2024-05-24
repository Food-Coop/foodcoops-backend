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
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.TooMuchBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;

@Component
public class RepresentationToEinkaufMapper implements Function<EinkaufRepresentation, EinkaufEntity> {
    @Autowired
    RepresentationToBestellungBuyMapper bestellungBuyMapper;
    
    @Autowired
    RepresentationToBestandBuyMapper bestandBuyMapper;

    @Autowired
    RepresentationToTooMuchBuyMapper tooMuchBuyMapper;




	@Override
	public EinkaufEntity apply(EinkaufRepresentation e) {
		// TODO Auto-generated method stub
		return new EinkaufEntity(e.getId(),
				e.getPersonId(),
				convertBestellungToList(e.getBestellungsEinkauf()),
				convertBestandToList(e.getBestandEinkauf()),
				convertTooMuchToList(e.getTooMuchEinkauf()),
				e.getDate(),
				e.getBreadPriceAtTime(),
				e.getFreshPriceAtTime(),
				e.getBestandPriceAtTime(),
				e.getTooMuchPriceAtTime(),
				e.getDeliveryCostAtTime());
		
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
	
	private List<TooMuchBuyEntity> convertTooMuchToList(Set<TooMuchBuyRepresentation> set) {
		if(set == null) {
			return null;
		}
		List<TooMuchBuyEntity> bestellung = new ArrayList<>();
		for(TooMuchBuyRepresentation b : set) {
			bestellung.add(tooMuchBuyMapper.apply(b));
		}
		return bestellung;
		
	}
}
