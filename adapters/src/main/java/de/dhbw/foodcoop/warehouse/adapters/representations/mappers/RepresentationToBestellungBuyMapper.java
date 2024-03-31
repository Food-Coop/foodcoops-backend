package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;

@Component
public class RepresentationToBestellungBuyMapper implements Function<BestellungBuyRepresentation, BestellungBuyEntity> {

	@Autowired
	private RepresentationToBestellungMapper bestellungMapper;
	

	@Override
	public BestellungBuyEntity apply(BestellungBuyRepresentation t) {
		// TODO Auto-generated method stub
		return new BestellungBuyEntity(t.getId(), bestellungMapper.apply(t.getBestellung()), t.getAmount());
	}


}