package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

@Component
public class RepresentationToBestandMapper implements Function<BestandRepresentation, BestandEntity> {

	@Autowired
	private RepresentationToEinheitMapper einheitMapper;
	
	@Autowired
	private RepresentationToKategorieMapper kategorieMapper;
	
	@Autowired
	private RepresentationToLagerbestandMapper lagerbestandMapper;
	
    @Autowired
    public RepresentationToBestandMapper() {
    }

    public BestandEntity update(BestandEntity oldBestand, BestandRepresentation newBestand) {
    	
    	
    	if(newBestand instanceof BrotBestandRepresentation) {
    		BrotBestandRepresentation bb = (BrotBestandRepresentation) newBestand;
    		return new BrotBestand(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), newBestand.getVerfuegbarkeit(), bb.getGewicht(), bb.getPreis());
    	} if(newBestand instanceof FrischBestandRepresentation) {
    		FrischBestandRepresentation fb = (FrischBestandRepresentation) newBestand;
    		return new FrischBestand(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), newBestand.getVerfuegbarkeit(), fb.getHerkunftsland(), fb.getGebindegroesse(), einheitMapper.apply(fb.getEinheit()), kategorieMapper.apply(fb.getKategorie()), fb.getPreis());
    	}
    	if(newBestand instanceof ProduktRepresentation) {
    		ProduktRepresentation pr = (ProduktRepresentation) newBestand;
    		return new Produkt(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), kategorieMapper.apply(pr.getKategorie()), lagerbestandMapper.apply(pr.getLagerbestand()));
    	}
    	
    	return null;
    }

    private String pickNewIfDefined(String oldValue, String newValue) {
        return replaceNullWithUndefined(newValue).equals("undefined") ? oldValue : newValue;
    }

    private String replaceNullWithUndefined(String oldValue) {
        return oldValue == null ? "undefined" : oldValue;
    }

	@Override
	public BestandEntity apply(BestandRepresentation t) {
		if(t instanceof BrotBestandRepresentation) {
			BrotBestandRepresentation bbr = (BrotBestandRepresentation) t;
			return new BrotBestand(bbr.getId(), bbr.getName(), bbr.getVerfuegbarkeit(), bbr.getGewicht(), bbr.getPreis());
		}  if(t instanceof FrischBestandRepresentation) {
			FrischBestandRepresentation bbr = (FrischBestandRepresentation) t;
			
			return new FrischBestand(bbr.getId(), bbr.getName(), bbr.getVerfuegbarkeit(), bbr.getHerkunftsland(), bbr.getGebindegroesse(), einheitMapper.apply(bbr.getEinheit()), kategorieMapper.apply(bbr.getKategorie()), bbr.getPreis());
		}
		if(t instanceof ProduktRepresentation) {
			ProduktRepresentation pr = (ProduktRepresentation) t;
			return new Produkt(pr.getId(), pr.getName(), kategorieMapper.apply(pr.getKategorie()), lagerbestandMapper.apply(pr.getLagerbestand()));
		}
		
		return null;
	}
}
