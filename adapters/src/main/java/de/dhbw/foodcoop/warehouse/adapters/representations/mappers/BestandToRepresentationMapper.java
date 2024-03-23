package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class BestandToRepresentationMapper implements Function<BestandEntity, BestandRepresentation> {

    @Autowired
    private KategorieToRepresentationMapper kategorieMapper;
    
    @Autowired
    private EinheitToRepresentationMapper einheitMapper;
    
    @Autowired
    private LagerbestandToRepresentationMapper lagerbestandMapper;
	
	@Lazy
    @Autowired
    public BestandToRepresentationMapper() {

    }

	@Override
	public BestandRepresentation apply(BestandEntity t) {
		// TODO Auto-generated method stub
		if(t instanceof BrotBestand) {
			BrotBestand bb = (BrotBestand)t;
			return new BrotBestandRepresentation(bb.getId(),
					bb.getName(),
					bb.getVerfuegbarkeit(),
					bb.getPreis(), bb.getGewicht());
		} if(t instanceof FrischBestand) {
			FrischBestand bb = (FrischBestand)t;
			return new FrischBestandRepresentation(bb.getId(),
					bb.getName(),
					bb.getVerfuegbarkeit(),
					bb.getHerkunftsland(),
					bb.getGebindegroesse(),
					einheitMapper.apply( bb.getEinheit()),
					kategorieMapper.apply(bb.getKategorie()),
					bb.getPreis());
		}
		if(t instanceof Produkt) {
			Produkt p = (Produkt) t;
			return new ProduktRepresentation(p.getId(), p.getName(), kategorieMapper.apply(p.getKategorie()), lagerbestandMapper.apply(p.getLagerbestand()), p.getPreis());
		}
		return null;
	}
}
