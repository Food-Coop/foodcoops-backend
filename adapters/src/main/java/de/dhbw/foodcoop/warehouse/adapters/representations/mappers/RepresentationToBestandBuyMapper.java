package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;

@Component
public class RepresentationToBestandBuyMapper implements Function<BestandBuyRepresentation, BestandBuyEntity> {

	@Autowired
	private RepresentationToBestandMapper bestandMapper;
	
	@Override
	public BestandBuyEntity apply(BestandBuyRepresentation t) {
		// TODO Auto-generated method stub
		return new BestandBuyEntity(t.getId(), bestandMapper.apply(t.getBestand()), t.getAmount());
	}


}
