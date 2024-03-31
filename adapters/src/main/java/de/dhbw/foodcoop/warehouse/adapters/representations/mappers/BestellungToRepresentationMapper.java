package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;

@Component
public class BestellungToRepresentationMapper implements Function<BestellungEntity, BestellungRepresentation> {


	@Autowired
	private BestandToRepresentationMapper bestandMapper;

    @Autowired
    public BestellungToRepresentationMapper() {
    }


	

	@Override
	public BestellungRepresentation apply(BestellungEntity t) {
		// TODO Auto-generated method stub
		if(t instanceof FrischBestellung) {
			FrischBestellung fb = (FrischBestellung) t;
			return new FrischBestellungRepresentation(fb.getId(),
					fb.getPersonId(),
					((FrischBestandRepresentation)bestandMapper.apply(fb.getFrischbestand())),
					fb.getBestellmenge(),
					fb.getDatum(),
					fb.isDone());
		}
		if(t instanceof BrotBestellung) {
			BrotBestellung bb = (BrotBestellung) t;
			return new BrotBestellungRepresentation(bb.getId(),
					bb.getPersonId(),
					((BrotBestandRepresentation) bestandMapper.apply(bb.getBrotBestand())),
					bb.getBestellmenge(),
					bb.getDatum(),
					bb.isDone());
		}
		return null;
	}
}
