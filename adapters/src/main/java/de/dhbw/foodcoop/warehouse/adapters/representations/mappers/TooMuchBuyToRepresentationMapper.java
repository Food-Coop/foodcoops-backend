package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.TooMuchBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;

@Component
public class TooMuchBuyToRepresentationMapper implements Function<TooMuchBuyEntity, TooMuchBuyRepresentation> {

	@Autowired
	private DiscrepancyToRepresentationMapper mapper; 
	
	
	@Override
	public TooMuchBuyRepresentation apply(TooMuchBuyEntity t) {
		// TODO Auto-generated method stub
		return new TooMuchBuyRepresentation(t.getId(), mapper.apply(t.getDiscrepancy()), t.getAmount());
	}

}
