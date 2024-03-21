package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandBuyRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.EinkaufRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;

@Component
public class EinkaufToRepresentationMapper implements Function<EinkaufEntity, EinkaufRepresentation> {
   

	@Autowired
	BestellungToRepresentationMapper bestellungMapper;
	
	@Autowired
	BestandBuyToRepresentationMapper bestandBuyMapper;
	@Override
	public EinkaufRepresentation apply(EinkaufEntity e) {
		 return new EinkaufRepresentation(e.getId(),
				 e.getPersonId(),
				 listToSetBestellung(e.getBestellungsEinkauf()),
				 listToSetBestandBuy( e.getBestandEinkauf()),
				 e.getDate(),
				 e.getBreadPriceAtTime(),
				 e.getFreshPriceAtTime(),
				 e.getBestandPriceAtTime());
	}
	
	
	private Set<BestellungRepresentation> listToSetBestellung(List<BestellungEntity> list) {
		if(list == null) {
			return null;
		}
		Set<BestellungRepresentation> set = new HashSet<>();
		for(BestellungEntity be : list) {
			set.add(bestellungMapper.apply(be));
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
}