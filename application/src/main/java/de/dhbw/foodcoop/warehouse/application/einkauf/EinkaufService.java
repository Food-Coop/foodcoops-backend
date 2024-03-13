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
import de.dhbw.foodcoop.warehouse.domain.repositories.BestandBuyRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufBestellungVergleichRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Service
public class EinkaufService {


	@Autowired
    private EinkaufRepository einkaufRepository;

    @Autowired
    private BrotBestellungRepository brotBestellungRepository;
    
    @Autowired
    private FrischBestellungRepository frischBestellungRepository;

    @Autowired
    private EinkaufBestellungVergleichRepository ebvRepository;
    
    @Autowired
    private BestandBuyRepository bestandBuyRepository;

    
    public EinkaufBestellungVergleich createCompareObjectForPersonOrder(BestellungEntity order, double realAmount) {
    		
        	EinkaufBestellungVergleich ebvObject = new EinkaufBestellungVergleich();
        	ebvObject.setId(UUID.randomUUID().toString());
        	ebvObject.setBestellung(order);
        	ebvObject.setReeleMenge(realAmount);
        	ebvObject.setReeleMengeAngegeben(true);
        	return ebvObject;
    }
    
    public BestandBuyEntity createBestandBuyEntityForPersonOrder(BestandEntity bestand, double amount) {
    	BestandBuyEntity bbe = new BestandBuyEntity();
    	bbe.setId(UUID.randomUUID().toString());
    	bbe.setAmount(amount);
    	bbe.setBestand(bestand);
    	return bbe;
    }
    

    public EinkaufEntity einkaufDurchführen(String personId, List<EinkaufBestellungVergleich> vergleiche, List<BestandBuyEntity> bestandBuy) {
    	
        EinkaufEntity einkauf = new EinkaufEntity();
        einkauf.setId(UUID.randomUUID().toString());
        einkauf.setPersonId(personId);
        einkauf.setDate(new Timestamp(System.currentTimeMillis()));
        einkauf = einkaufRepository.speichern(einkauf);
        for(BestandBuyEntity bbe : bestandBuy) {
        	bbe.setEinkauf(einkauf);
        	bestandBuyRepository.speichern(bbe);
            if(einkauf.getBestandEinkauf() == null) {
            	einkauf.setBestandEinkauf(new ArrayList<BestandBuyEntity>());
            }
        	einkauf.getBestandEinkauf().add(bbe);
        }
        

        for (EinkaufBestellungVergleich ebv : vergleiche) {
            
            ebv.setEinkauf(einkauf);
            // Setze die reeleMenge und reeleMengeAngegeben basierend auf dem Vergleich...

            // Speichere den Vergleich in der Datenbank
            ebvRepository.speichern(ebv);

            // Füge den Vergleich dem Einkauf hinzu
            if(einkauf.getEinkauf() == null) {
            	einkauf.setEinkauf(new ArrayList<EinkaufBestellungVergleich>());
            }
            einkauf.getEinkauf().add(ebv);
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
    	for(EinkaufBestellungVergleich ebv : einkauf.getEinkauf()) {
    		double real = ebv.getReeleMenge();
    		if(ebv.getBestellung() instanceof BrotBestellung) {
    			BrotBestellung brot = (BrotBestellung) ebv.getBestellung();
    			price = price + real * brot.getBrotBestand().getPreis();
    		}
    	}
    	for(BestandBuyEntity be : einkauf.getBestandEinkauf()) {
    		if(be.getBestand() instanceof BrotBestand) {
    			price = price + be.getBestand().getPreis() * be.getAmount();
    		}
    	}
    	return price;
    }
    
    public double calculatePriceForBestandBuy(EinkaufEntity einkauf) {
    	double price = 0d;
    	for(BestandBuyEntity be : einkauf.getBestandEinkauf()) {
    		if(be.getBestand() instanceof Produkt) {
    			price = price + be.getBestand().getPreis() * be.getAmount();
    		}
    	}
    	return price;
    }
    
    public double calculatePriceForFresh(EinkaufEntity einkauf) {
    	double price = 0d;
    	for(EinkaufBestellungVergleich ebv : einkauf.getEinkauf()) {
    		double real = ebv.getReeleMenge();
    		if(ebv.getBestellung() instanceof FrischBestellung) {
    			FrischBestellung frisch = (FrischBestellung) ebv.getBestellung();
    			price = price + real * frisch.getFrischbestand().getPreis();
    		}
    	}
    	for(BestandBuyEntity be : einkauf.getBestandEinkauf()) {
    		if(be.getBestand() instanceof FrischBestand) {
    			price = price + be.getBestand().getPreis() * be.getAmount();
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
