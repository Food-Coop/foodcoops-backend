package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.BestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.BrotBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.FrischBestandRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.ProduktRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

@Component
public class RepresentationToBestandMapper implements Function<BestandRepresentation, BestandEntity> {

	@Autowired
	private RepresentationToLagerbestandMapper lagerbestandMapper;
	
	@Autowired
	private EinheitService einheitService;
	
	@Autowired
	private KategorieService kategorieService;
	
    @Autowired
    public RepresentationToBestandMapper() {
    }

    public BestandEntity update(BestandEntity oldBestand, BestandRepresentation newBestand) {
    	
    	
    	if(newBestand instanceof BrotBestandRepresentation) {
    		BrotBestandRepresentation bb = (BrotBestandRepresentation) newBestand;
    		return new BrotBestand(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), newBestand.getVerfuegbarkeit(), bb.getGewicht(), bb.getPreis());
    	} if(newBestand instanceof FrischBestandRepresentation) {
    		FrischBestandRepresentation fb = (FrischBestandRepresentation) newBestand;
    		Einheit einheit = einheitService.findById(fb.getEinheit().getId()).orElseThrow();
			Kategorie kategorie = kategorieService.findById(fb.getKategorie().getId()).orElseThrow();
    		return new FrischBestand(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), newBestand.getVerfuegbarkeit(), fb.getHerkunftsland(), fb.getGebindegroesse(), einheit, kategorie, fb.getPreis(), fb.getVerband());
    	}
    	if(newBestand instanceof ProduktRepresentation) {
    		ProduktRepresentation pr = (ProduktRepresentation) newBestand;
    		Kategorie kategorie = kategorieService.findById(pr.getKategorie().getId()).orElseThrow();
    		Produkt p = new Produkt(oldBestand.getId(), pickNewIfDefined(oldBestand.getName(), newBestand.getName()), pr.getProduktname(), kategorie, lagerbestandMapper.apply(pr.getLagerbestand()), pr.getPreis());
    		p.setPreis(pr.getPreis());
    		p.setVerfuegbarkeit(pr.getVerfuegbarkeit());
    		return p;
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
			Einheit einheit = einheitService.findById(bbr.getEinheit().getId()).orElseThrow();
			Kategorie kategorie = kategorieService.findById(bbr.getKategorie().getId()).orElseThrow();
			return new FrischBestand(bbr.getId(), bbr.getName(), bbr.getVerfuegbarkeit(), bbr.getHerkunftsland(), bbr.getGebindegroesse(), einheit, kategorie, bbr.getPreis(), bbr.getVerband());
		}
		if(t instanceof ProduktRepresentation) {
			ProduktRepresentation pr = (ProduktRepresentation) t;
			Kategorie kategorie = kategorieService.findById(pr.getKategorie().getId()).orElseThrow();
    		Produkt p = new Produkt(pr.getId(), pr.getName(), pr.getProduktname(), kategorie, lagerbestandMapper.apply(pr.getLagerbestand()), pr.getPreis());
    		p.setPreis(pr.getPreis());
    		p.setVerfuegbarkeit(pr.getVerfuegbarkeit());
    		return p;
		}
		
		return null;
	}
}
