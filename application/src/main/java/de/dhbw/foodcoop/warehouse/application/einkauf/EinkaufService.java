package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;

@Service
public class EinkaufService {


	@Autowired
    private EinkaufRepository einkaufRepository;

    public BestandBuyEntity createBestandBuyEntityForPersonOrder(Produkt bestand, double amount) {
    	BestandBuyEntity bbe = new BestandBuyEntity();
    	bbe.setId(UUID.randomUUID().toString());
    	bbe.setAmount(amount);
    	bbe.setBestand(bestand);
    	return bbe;
    }
    

    public EinkaufEntity einkaufDurchführen( String personId, List<BestellungBuyEntity> vergleiche, List<BestandBuyEntity> bestandBuy) throws Exception {

        EinkaufEntity einkauf = new EinkaufEntity();
        einkauf.setId(UUID.randomUUID().toString());
        einkauf.setPersonId(personId);

        einkauf.setDate(LocalDateTime.now());
        einkauf = einkaufRepository.speichern(einkauf);
        if(bestandBuy != null) {
	        for(BestandBuyEntity bbe : bestandBuy) {
	        	//bbe.setEinkauf(einkauf);
	        //	bestandBuyRepository.speichern(bbe);
	            if(einkauf.getBestandEinkauf() == null) {
	            	einkauf.setBestandEinkauf(new ArrayList<BestandBuyEntity>());
	            }
	            	if(bbe.getBestand().getLagerbestand().getIstLagerbestand() - bbe.getAmount() < 0) {
	            		einkaufRepository.deleteById(einkauf.getId());
	            		throw new Exception("Insufficient Lagerbestand!");
	            	}
	            	bbe.getBestand().getLagerbestand().setIstLagerbestand(bbe.getBestand().getLagerbestand().getIstLagerbestand() - bbe.getAmount());
	        		einkauf.getBestandEinkauf().add(bbe);
	        }
        }
        if(vergleiche != null) {
        	for (BestellungBuyEntity ebv : vergleiche) {
        		
            	// Füge den Vergleich dem Einkauf hinzu
            	if(einkauf.getBestellungsEinkauf() == null) {
            		einkauf.setBestellungsEinkauf(new ArrayList<BestellungBuyEntity>());
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
	    	for(BestellungBuyEntity ebv : einkauf.getBestellungsEinkauf()) {
	    		if(ebv.getBestellung() instanceof BrotBestellung) {
	    			double real = ebv.getAmount();
	    			BrotBestellung brot = (BrotBestellung) ebv.getBestellung();
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
	    	for(BestellungBuyEntity ebv : einkauf.getBestellungsEinkauf()) {
	    		if(ebv.getBestellung() instanceof FrischBestellung) {
	    			double real = ebv.getAmount();
	    			FrischBestellung frisch = (FrischBestellung) ebv.getBestellung();
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
