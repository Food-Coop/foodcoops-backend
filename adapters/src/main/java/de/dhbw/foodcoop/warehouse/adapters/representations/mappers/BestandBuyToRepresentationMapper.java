package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandBuyRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;

@Component
public class BestandBuyToRepresentationMapper implements Function<BestandBuyEntity, BestandBuyRepresentation>{

	@Autowired
	BestandToRepresentationMapper bestandMapper;
	
	@Override
	public BestandBuyRepresentation apply(BestandBuyEntity t) {
		// TODO Auto-generated method stub
		return new BestandBuyRepresentation(t.getId(),
				(ProduktRepresentation)bestandMapper.apply(t.getBestand()), t.getAmount());
	}

}
