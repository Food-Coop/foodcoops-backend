package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.TooMuchBuyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@Component
public class RepresentationToTooMuchBuyMapper  implements Function<TooMuchBuyRepresentation, TooMuchBuyEntity> {

	@Autowired
	private RepresentationToDiscrepancyMapper mapper; 
	
	@Override
	public TooMuchBuyEntity apply(TooMuchBuyRepresentation t) {
		// TODO Auto-generated method stub
		return new TooMuchBuyEntity(t.getId(), mapper.apply(t.getDiscrepancy()), t.getAmount());
	}

}
