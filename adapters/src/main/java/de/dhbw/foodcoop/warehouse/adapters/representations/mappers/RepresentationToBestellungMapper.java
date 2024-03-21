package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestellungRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.exceptions.BrotBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.FrischBestandNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.exceptions.ProduktNotFoundException;

@Component
public class RepresentationToBestellungMapper implements Function<BestellungRepresentation, BestellungEntity> {
    private final BrotBestandService brotBestandService;

    @Autowired
    private  FrischBestandService frischBestandService;
    
    
    @Autowired
    public RepresentationToBestellungMapper(BrotBestandService brotBestandService) {
        this.brotBestandService = brotBestandService;
    }



    public BestellungEntity update(BestellungEntity oldBestellung, BestellungRepresentation newBestellung) {
      
    	if(newBestellung instanceof BrotBestellungRepresentation) {
    		
    	BrotBestellungRepresentation bbr = (BrotBestellungRepresentation) newBestellung;
    	BrotBestand brotBestand = brotBestandService.findById(bbr.getBrotbestand().getId()).orElseThrow
                (() -> new BrotBestandNotFoundException(bbr.getBrotbestand().getId()));
        return new BrotBestellung(
        		oldBestellung.getId(),
                bbr.getPersonId(),
                brotBestand,
                bbr.getBestellmenge(),
                bbr.getDatum()
        );
    	}
    	
    	if(newBestellung instanceof FrischBestellungRepresentation) {
    		
    	FrischBestellungRepresentation fbr = (FrischBestellungRepresentation) newBestellung;
    	FrischBestand frischBestand = frischBestandService.findById(fbr.getFrischbestand().getId()).orElseThrow
                (() -> new FrischBestandNotFoundException(fbr.getFrischbestand().getId()));
        return new FrischBestellung(
        		oldBestellung.getId(),
                fbr.getPersonId(),
                frischBestand,
                fbr.getBestellmenge(),
                fbr.getDatum()
        );
    	}
    	return null;
    }

	@Override
	public BestellungEntity apply(BestellungRepresentation t) {
		if(t instanceof BrotBestellungRepresentation) {
			BrotBestellungRepresentation bb = (BrotBestellungRepresentation) t;
	        BrotBestand brotBestand = brotBestandService.findById(bb.getBrotbestand().getId()).orElseThrow
	                (() -> new BrotBestandNotFoundException(bb.getBrotbestand().getId()));
	        return new BrotBestellung(
	                bb.getId(),
	                bb.getPersonId(),
	                brotBestand,
	                bb.getBestellmenge(),
	                bb.getDatum()
	        );
		}
		if(t instanceof FrischBestellungRepresentation) {
			FrischBestellungRepresentation bb = (FrischBestellungRepresentation) t;
	        FrischBestand frischBestand = frischBestandService.findById(bb.getFrischbestand().getId()).orElseThrow
	                (() -> new BrotBestandNotFoundException(bb.getFrischbestand().getId()));
	        return new FrischBestellung(
	                bb.getId(),
	                bb.getPersonId(),
	                frischBestand,
	                bb.getBestellmenge(),
	                bb.getDatum()
	        );
		}
		return null;
	}
}

