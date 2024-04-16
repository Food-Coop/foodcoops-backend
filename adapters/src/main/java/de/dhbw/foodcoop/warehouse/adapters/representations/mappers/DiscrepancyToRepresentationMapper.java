package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.DiscrepancyRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;

@Component
public class DiscrepancyToRepresentationMapper implements Function<DiscrepancyEntity, DiscrepancyRepresentation> {

	@Autowired
	private BestandToRepresentationMapper mapper;
	
	@Override
	public DiscrepancyRepresentation apply(DiscrepancyEntity t) {
		return new DiscrepancyRepresentation(t.getId(), mapper.apply(t.getBestand()), t.getZuBestellendeGebinde(), t.getZuVielzuWenig(), t.getGewollteMenge());
	}

}
