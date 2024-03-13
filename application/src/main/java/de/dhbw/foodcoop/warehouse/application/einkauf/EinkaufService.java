package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.BestellungEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.repositories.BrotBestellungRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufBestellungVergleichRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.FrischBestellungRepository;
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

    public EinkaufEntity einkaufDurchführen(String personId, Timestamp einkaufsDatum, List<EinkaufBestellungVergleich> vergleiche) {
        EinkaufEntity einkauf = new EinkaufEntity();
        einkauf.setId(UUID.randomUUID().toString());
        einkauf.setPersonId(personId);
        einkauf.setDate(einkaufsDatum);
        // Weitere Einkaufsdetails setzen...

        // Finde alle Bestellungen dieser Person
        //Anpassen mit Datum nach ..
        List<FrischBestellung> frischBestellungen = frischBestellungRepository.findeAlleVonPerson(personId);
        List<BrotBestellung> brotBestellungen = brotBestellungRepository.alleVonPerson(personId);
        List<BestellungEntity> bestellungen = Stream.concat(frischBestellungen.stream(), brotBestellungen.stream())
                .collect(Collectors.toList());
        

        for (BestellungEntity bestellung : bestellungen) {
            EinkaufBestellungVergleich vergleich = new EinkaufBestellungVergleich();
            vergleich.setId(UUID.randomUUID().toString());
            vergleich.setBestellung(bestellung);
            vergleich.setEinkauf(einkauf);
            // Setze die reeleMenge und reeleMengeAngegeben basierend auf dem Vergleich...

            // Speichere den Vergleich in der Datenbank
            ebvRepository.speichern(vergleich);

            // Füge den Vergleich dem Einkauf hinzu
            einkauf.getEinkauf().add(vergleich);
        }

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
    //TODO ÜBERARBEITEN; FUNKTIONIERT NICHT!!!!!
    public EinkaufEntity executeShopping(String personId, List<EinkaufBestellungVergleich> updatedOrders) {
    //	Person p = personService.findById(personId).orElseThrow();
    	List<EinkaufBestellungVergleich> nonFullfiledOrders = new ArrayList<>();
    	for(EinkaufBestellungVergleich ebv : updatedOrders) {
    		if(!ebv.isReeleMengeAngegeben()) {
    			nonFullfiledOrders.add(ebv);
    		}
    	}
    	
    	updatedOrders.removeAll(nonFullfiledOrders);
    	//p.setBestellungen(nonFullfiledOrders);
    	//personService.save(p);
    	EinkaufEntity ee = new EinkaufEntity(UUID.randomUUID().toString(), personId, updatedOrders, Timestamp.from(Instant.now()));
    	ee.setBreadPriceAtTime(calculatePriceForBread(ee));
    	ee.setFreshPriceAtTime(calculatePriceForFresh(ee));
    	return einkaufRepository.speichern(ee);
    	
    }
    
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
    	return calculatePriceForBread(einkauf) + calculatePriceForFresh(einkauf);
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
    	return price;
    }
    
//    public List<EinkaufBestellungVergleich> loadOpenOrders(String personId) {
//    	Person p = personService.findById(personId).orElseThrow();
//        return p.getBestellungen().stream() 
//                .filter(b -> !b.isReeleMengeAngegeben()) 
//                .collect(Collectors.toList());
//    }
	
}
