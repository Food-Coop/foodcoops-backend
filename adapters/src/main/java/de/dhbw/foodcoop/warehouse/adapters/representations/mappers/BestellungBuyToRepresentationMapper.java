package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;

@Component
public class BestellungBuyToRepresentationMapper implements Function<BestellungBuyEntity, BestellungBuyRepresentation>{

	@Autowired
	BestellungToRepresentationMapper bestellungMapper;
	


	@Override
	public BestellungBuyRepresentation apply(BestellungBuyEntity arg0) {
		// TODO Auto-generated method stub
		return new BestellungBuyRepresentation(arg0.getId(), bestellungMapper.apply(arg0.getBestellung()), arg0.getAmount());
	}

}
