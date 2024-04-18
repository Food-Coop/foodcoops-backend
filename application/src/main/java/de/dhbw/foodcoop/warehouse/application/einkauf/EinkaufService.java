package de.dhbw.foodcoop.warehouse.application.einkauf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.application.bestellungsliste.BestellÜbersichtService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.BestandBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellUebersicht;
import de.dhbw.foodcoop.warehouse.domain.entities.BestellungBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.EinkaufEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.entities.TooMuchBuyEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.EinkaufRepository;

@Service
public class EinkaufService {


	
	@Autowired
    private EinkaufRepository einkaufRepository;
	
	@Autowired
    private BestellÜbersichtService service;
	@Autowired
    private FrischBestellungService frischService;
	
	@Autowired
	private DeadlineService deadlineService;

    public BestandBuyEntity createBestandBuyEntityForPersonOrder(Produkt bestand, double amount) {
    	BestandBuyEntity bbe = new BestandBuyEntity();
    	bbe.setId(UUID.randomUUID().toString());
    	bbe.setAmount(amount);
    	bbe.setBestand(bestand);
    	return bbe;
    }
    

    public EinkaufEntity einkaufDurchführen( String personId, List<BestellungBuyEntity> vergleiche, List<BestandBuyEntity> bestandBuy, List<TooMuchBuyEntity> tooMuchBuy) throws Exception {
    	
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
        
        if(tooMuchBuy != null) {
        	for(TooMuchBuyEntity e: tooMuchBuy) {
        		if(einkauf.getTooMuchEinkauf() == null) {
        			einkauf.setTooMuchEinkauf(new ArrayList<TooMuchBuyEntity>());
        		}
        		e.getDiscrepancy().setZuVielzuWenig(e.getDiscrepancy().getZuVielzuWenig() - (float)e.getAmount());
        		einkauf.getTooMuchEinkauf().add(e);
        	}
        }
        

        
        einkauf.setTooMuchPriceAtTime(calculatePriceForTooMuch(einkauf));
        einkauf.setBestandPriceAtTime(calculatePriceForBestandBuy(einkauf));
        einkauf.setBreadPriceAtTime(calculatePriceForBread(einkauf));
        einkauf.setFreshPriceAtTime(calculatePriceForFresh(einkauf));
        
        BestellUebersicht be = service.getLastUebersicht();
        if(be != null) {
        	List<DiscrepancyEntity> discrepancies = be.getDiscrepancy();
        	List<BestellungBuyEntity> bestellungen = einkauf.getBestellungsEinkauf();
        			
        	Optional<Deadline> date1 = deadlineService.getByPosition(0);
        	Optional<Deadline> date2 = deadlineService.getByPosition(1);
        	
        	if(date1.isPresent()) {
        		if(date2.isPresent()) {
        	
        	
        	LocalDateTime datum1 = date1.get().getDatum();
        	LocalDateTime datum2 = date2.get().getDatum();
         	List<FrischBestellung> frischBestellungen = frischService.findByDateBetween(datum1, datum2, personId);
         	String eID = einkauf.getId();
         	List<EinkaufEntity> einkaufeFromPerson = einkaufRepository.alleAktuellenVonPerson(datum1, personId).stream().filter(t -> t.getId() != eID).collect(Collectors.toList());
         	HashMap<FrischBestand, Double> mapAmountForOrder = new HashMap<>();
         	 if(vergleiche != null) {
         		for (BestellungBuyEntity ebv : vergleiche) {
         			if(ebv.getBestellung() != null && ebv.getBestellung() instanceof FrischBestellung)  {
         				mapAmountForOrder.merge(((FrischBestellung) ebv.getBestellung()).getFrischbestand(), ebv.getAmount(), Double::sum);
         			}
         		}
         		einkaufeFromPerson.forEach(t -> {
         			if(t.getBestellungsEinkauf() != null) {
         				for(BestellungBuyEntity eb : t.getBestellungsEinkauf()) {
         					if(eb.getBestellung() != null && eb.getBestellung() instanceof FrischBestellung) {
         						mapAmountForOrder.merge(((FrischBestellung) eb.getBestellung()).getFrischbestand(), eb.getAmount(), Double::sum);
         					}
         				}
         			}
         		});
         	 }
         	
         	
         	 
         	 
         	 if(bestellungen != null) {
         	Set<Kategorie> katSet = new HashSet<>();
         	bestellungen.forEach(t -> {
         		if(t.getBestellung() instanceof FrischBestellung) {
         			FrischBestellung f = (FrischBestellung) t.getBestellung();
         			if(f.getFrischbestand().getKategorie().isMixable()) {
         				katSet.add(f.getFrischbestand().getKategorie());
         			} else  {
         				double sumOrderedFromPerson =  (Math.round( t.getBestellung().getBestellmenge() * 100.0) / 100.0); 
         				double sumTakenFromPerson = (Math.round( mapAmountForOrder.get(f.getFrischbestand()) * 100.0) / 100.0);
             	    		BestellungBuyEntity  bbe = null;
             	    		for(BestellungBuyEntity bestellung : bestellungen) {
             	    			if(bestellung.getBestellung().getId().equalsIgnoreCase(t.getBestellung().getId())) {
             	    				bbe = bestellung;
             	    				break;
             	    			}
             	    		}
             	    		if(bbe != null) {
             	    			double sumToAdjust; 
             	    			if(bbe.getBestellung().isDone()) {
             	    				sumToAdjust = -t.getAmount();
             	    			} else {
             	    				 sumToAdjust = sumOrderedFromPerson - sumTakenFromPerson;
             	    				bbe.getBestellung().setDone(true);
             	    			}
             	    			adjustNonMixDiscrepency(discrepancies, sumToAdjust, bbe );
             	    		}
         			}
         		}
         	});
         	
         	for(Kategorie k : katSet) {
         		double sumOrderedFromPerson = (Math.round( sumByKategorie(k, frischBestellungen) * 100.0) / 100.0);
         	    double sumTakenFromPerson = (Math.round( sumBestellungBuyByKategorie(k, bestellungen, mapAmountForOrder) * 100.0) / 100.0);
         	    			double sumToAdjust = sumOrderedFromPerson - sumTakenFromPerson;
         	    			boolean isDone = true;
         	    			if(bestellungen != null ) {
         	    				for(BestellungBuyEntity b : bestellungen) {
         	    					if(!b.getBestellung().isDone()) {
         	    						isDone = false;
         	    						break;
         	    					}
         	    				}
         	    			}
         	    			if(isDone) {
         	    				sumToAdjust = -sumTakenFromPerson;
         	    			}
         	    			
         	    			double rest = adjustMixDiscrepency(discrepancies, sumToAdjust, k);
         	    		//REST BEHANDELN
         	    			handleRest(discrepancies, rest, k);
         			}

        		}
        		}
        	}
        }
       EinkaufEntity e = einkaufRepository.speichern(einkauf);
       if(be != null) {
        service.update(be);
       }
        // Speichere den Einkauf in der Datenbank
        return e;
    }

    private double adjustNonMixDiscrepency(List<DiscrepancyEntity> discrepancies, double sumToAdjust, BestellungBuyEntity bbe ) {
    	for(DiscrepancyEntity d : discrepancies) {
    		if(d.getZuVielzuWenig() == 0) continue;
    		if(d.getBestand() instanceof FrischBestand) {
    			FrischBestand frisch = (FrischBestand) d.getBestand();
    			if(bbe.getBestellung() instanceof FrischBestellung) {
    				FrischBestellung fb = (FrischBestellung) bbe.getBestellung();
    			if(fb.getFrischbestand().getId().equalsIgnoreCase(frisch.getId())) {
    			
    				//HIER WEITER MACHEN!!
    			
    				d.setZuVielzuWenig((float) (d.getZuVielzuWenig() + sumToAdjust));
    				return 0;
    			}
    		}
    		}
    	}
    	//Eventuell neues Discrepancy objekt anlegen, TODO: Naschauen ob auch 0er in db gespeichert sind
    	return sumToAdjust;
    }

    
    private double adjustMixDiscrepency(List<DiscrepancyEntity> discrepancies, double sumToAdjust, Kategorie k) {
    	for(DiscrepancyEntity d : discrepancies) {
    		if(d.getZuVielzuWenig() == 0) continue;
    		if(d.getBestand() instanceof FrischBestand) {
    			FrischBestand frisch = (FrischBestand) d.getBestand();
    			if(k.getId().equalsIgnoreCase(frisch.getKategorie().getId())) {
    				if(d.getZuVielzuWenig() + sumToAdjust == 0) {
    					d.setZuVielzuWenig(0);
    					sumToAdjust = 0;
    					break;
    				}
    				if(d.getZuVielzuWenig() < 0) {
    					if(d.getZuVielzuWenig() + sumToAdjust > 0) {
    						sumToAdjust = d.getZuVielzuWenig() + sumToAdjust;
    						d.setZuVielzuWenig(0);
    					} else if(d.getZuVielzuWenig() + sumToAdjust < 0) {
    						d.setZuVielzuWenig((float) (d.getZuVielzuWenig() + sumToAdjust));
    						sumToAdjust = 0;
    					}
    					
    				} else {
    					if(d.getZuVielzuWenig() + sumToAdjust < 0) {
    						sumToAdjust = d.getZuVielzuWenig() + sumToAdjust;
    						d.setZuVielzuWenig(0);
    					} else if(d.getZuVielzuWenig() + sumToAdjust > 0) {
    						d.setZuVielzuWenig((float) (d.getZuVielzuWenig() + sumToAdjust));
    						sumToAdjust = 0;
    					}
    				}
    			}
    		}
    	}
    	return sumToAdjust;
    }

   private void handleRest(List<DiscrepancyEntity> discrepancies, double rest, Kategorie k) {
	   for(DiscrepancyEntity d : discrepancies) {
   		if(d.getZuVielzuWenig() == 0) continue;
   		if(d.getBestand() instanceof FrischBestand) {
   			FrischBestand frisch = (FrischBestand) d.getBestand();
   			if(k.getId().equalsIgnoreCase(frisch.getKategorie().getId())) {
   				d.setZuVielzuWenig(d.getZuVielzuWenig() + (float)rest);
   				
   			 }
   		  }
	   }
   }

	private double sumByKategorie(Kategorie k, List<FrischBestellung> bestellungen) {
    	return bestellungen.stream().filter(t -> t.getFrischbestand().getKategorie().getId().equalsIgnoreCase(k.getId())).mapToDouble( x -> x.getBestellmenge()).sum();
    }
    
    private double sumBestellungBuyByKategorie(Kategorie k, List<BestellungBuyEntity> bestellungen, HashMap<FrischBestand, Double> map) {
    	double counter = 0;
    	for(BestellungBuyEntity bbe : bestellungen) {
    		if(bbe.getBestellung() instanceof FrischBestellung) {
    			FrischBestellung f = (FrischBestellung) bbe.getBestellung();
    			if(f.getFrischbestand().getKategorie().getId().equalsIgnoreCase(k.getId()))  {
    				counter = counter + bbe.getAmount() + map.get(f.getFrischbestand());
    				
    			}
    		}
    	}
    	return counter;
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
    
    public double calculatePriceForTooMuch(EinkaufEntity einkauf) {
    	double price = 0d;
    	if(einkauf.getTooMuchEinkauf() != null) { 
	    	for(TooMuchBuyEntity ebv : einkauf.getTooMuchEinkauf()) {
	    			double real = ebv.getAmount();
	    			price = price + real * ebv.getDiscrepancy().getBestand().getPreis();
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
