package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;

@Service
public class EinkaufService {


	@Autowired
    private EinkaufRepository einkaufRepository;

    public BestandBuyEntity createBestandBuyEntityForPersonOrder(BestandEntity bestand, double amount) {
    	BestandBuyEntity bbe = new BestandBuyEntity();
    	bbe.setId(UUID.randomUUID().toString());
    	bbe.setAmount(amount);
    	bbe.setBestand(bestand);
    	return bbe;
    }
    

    public EinkaufEntity einkaufDurchführen( String personId, List<BestellungEntity> vergleiche, List<BestandBuyEntity> bestandBuy) {

        EinkaufEntity einkauf = new EinkaufEntity();
        einkauf.setId(UUID.randomUUID().toString());
        einkauf.setPersonId(personId);
        einkauf.setDate(new Timestamp(System.currentTimeMillis()));
        einkauf = einkaufRepository.speichern(einkauf);
        if(bestandBuy != null) {
	        for(BestandBuyEntity bbe : bestandBuy) {
	        	//bbe.setEinkauf(einkauf);
	        //	bestandBuyRepository.speichern(bbe);
	            if(einkauf.getBestandEinkauf() == null) {
	            	einkauf.setBestandEinkauf(new ArrayList<BestandBuyEntity>());
	            }
	        		einkauf.getBestandEinkauf().add(bbe);
	        }
        }
        if(vergleiche != null) {
        	for (BestellungEntity ebv : vergleiche) {
            	// Füge den Vergleich dem Einkauf hinzu
            	if(einkauf.getBestellungsEinkauf() == null) {
            		einkauf.setBestellungsEinkauf(new ArrayList<BestellungEntity>());
            	}
            		einkauf.getBestellungsEinkauf().add(ebv);
        	}
        }
        

        einkauf.setBestandPriceAtTime(calculatePriceForBestandBuy(einkauf));
        einkauf.setBreadPriceAtTime(calculatePriceForBread(einkauf));
        einkauf.setFreshPriceAtTime(calculatePriceForFresh(einkauf));
        // Speichere den Einkauf in der Datenbank
        return einkaufRepository.speichern(einkauf);
    }

    
    /** Es muss sich erst alle offenen Orders über loadOpenOrders gezogen werden, dann kann jeweils die echt genommene Menge eingetragen werden
     * bei jedem Object und die fertige liste hier übergeben werden.
     * isReeleMengeAngegeben setzten falls User die angegeben hat, sonst wirds bei open orders wieder angezeigt!
     * 
     * 
     * @param personId
     * @param updatedOrders
     */
  
    
    public List<EinkaufEntity> findAllFromPerson(String personId) {
    	return einkaufRepository.alleVonPerson(personId);
    }

    public List<EinkaufEntity> all() {
    	return einkaufRepository.alle();
    }
    
    public EinkaufEntity findById(String id) {
    	return einkaufRepository.findeMitId(id).orElseThrow();
    }
    
    public void deleteById(String id) {
    	einkaufRepository.deleteById(id);
    }
    public double calculateTotalPrice(EinkaufEntity einkauf) {
    	return calculatePriceForBread(einkauf) + calculatePriceForFresh(einkauf) + calculatePriceForBestandBuy(einkauf);
    }
    
    public double calculatePriceForBread(EinkaufEntity einkauf) {
    	double price = 0d;
    	if(einkauf.getBestellungsEinkauf() != null) {
	    	for(BestellungEntity ebv : einkauf.getBestellungsEinkauf()) {
	    		if(ebv instanceof BrotBestellung) {
	    			double real = ebv.getReeleMenge();
	    			BrotBestellung brot = (BrotBestellung) ebv;
	    			price = price + real * brot.getBrotBestand().getPreis();
	    		}
	    	}
    	}

    	return price;
    }
    
    public double calculatePriceForBestandBuy(EinkaufEntity einkauf) {
    	double price = 0d;
    	if(einkauf.getBestandEinkauf() == null) {
    		return price;
    	}
    	for(BestandBuyEntity be : einkauf.getBestandEinkauf()) {
    		if(be.getBestand() instanceof Produkt) {
    			price = price + be.getBestand().getPreis() * be.getAmount();
    		}
    	}
    	return price;
    }
    
    public double calculatePriceForFresh(EinkaufEntity einkauf) {
    	double price = 0d;
    	if(einkauf.getBestellungsEinkauf() != null) { 
	    	for(BestellungEntity ebv : einkauf.getBestellungsEinkauf()) {
	    		if(ebv instanceof FrischBestellung) {
	    			double real = ebv.getReeleMenge();
	    			FrischBestellung frisch = (FrischBestellung) ebv;
	    			price = price + real * frisch.getFrischbestand().getPreis();
	    		}
	    	}
    	}

    	return price;
    }
    
//    public List<EinkaufBestellungVergleich> loadOpenOrders(String personId) {
//    	Person p = personService.findById(personId).orElseThrow();
//        return p.getBestellungen().stream() 
//                .filter(b -> !b.isReeleMengeAngegeben()) 
//                .collect(Collectors.toList());
//    }
	
}
