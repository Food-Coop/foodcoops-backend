package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.DiscrepancyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;

@Component
public class RepresentationToDiscrepancyMapper implements Function<DiscrepancyRepresentation, DiscrepancyEntity> {
	
	@Autowired
	private RepresentationToBestandMapper mapper;
	
	
	@Override
	public DiscrepancyEntity apply(DiscrepancyRepresentation t) {
		// TODO Auto-generated method stub
		return new DiscrepancyEntity(t.getId(), mapper.apply(t.getBestand()), t.getZuBestellendeGebinde(), t.getZuVielzuWenig(), t.getGewollteMenge());
	}

}
