package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.HashSet;
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
public class EinkaufToRepresentationMapper implements Function<EinkaufEntity, EinkaufRepresentation> {
   

	@Autowired
	BestellungBuyToRepresentationMapper bestellungBuyMapper;
	
	@Autowired
	BestandBuyToRepresentationMapper bestandBuyMapper;
	
	@Autowired
	TooMuchBuyToRepresentationMapper tooMuchBuyMapper;
	
	
	@Override
	public EinkaufRepresentation apply(EinkaufEntity e) {
		 return new EinkaufRepresentation(e.getId(),
				 e.getPersonId(),
				 listToSetBestellung(e.getBestellungsEinkauf()),
				 listToSetBestandBuy( e.getBestandEinkauf()),
				 listToSetTooMuchBuy(e.getTooMuchEinkauf()),
				 e.getDate(),
				 e.getBreadPriceAtTime(),
				 e.getFreshPriceAtTime(),
				 e.getBestandPriceAtTime(),
				 e.getTooMuchPriceAtTime());
	}
	
	
	private Set<BestellungBuyRepresentation> listToSetBestellung(List<BestellungBuyEntity> list) {
		if(list == null) {
			return null;
		}
		Set<BestellungBuyRepresentation> set = new HashSet<>();
		for(BestellungBuyEntity be : list) {
			set.add(bestellungBuyMapper.apply(be));
		}
		return set;
	}
	private Set<BestandBuyRepresentation> listToSetBestandBuy(List<BestandBuyEntity> list) {
		if(list == null) {
			return null;
		}
		Set<BestandBuyRepresentation> set = new HashSet<>();
		for(BestandBuyEntity be : list) {
			set.add(bestandBuyMapper.apply(be));
		}
		return set;
	}
	
	private Set<TooMuchBuyRepresentation> listToSetTooMuchBuy(List<TooMuchBuyEntity> list) {
		if(list == null) {
			return null;
		}
		Set<TooMuchBuyRepresentation> set = new HashSet<>();
		for(TooMuchBuyEntity be : list) {
			set.add(tooMuchBuyMapper.apply(be));
		}
		return set;
	}
}