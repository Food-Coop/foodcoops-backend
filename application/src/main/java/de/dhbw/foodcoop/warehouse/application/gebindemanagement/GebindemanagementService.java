package de.dhbw.foodcoop.warehouse.application.gebindemanagement;

import java.lang.invoke.WrongMethodTypeException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestellungService;
import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestellung;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

public class GebindemanagementService {
	
	@Autowired
	private DeadlineService deadlineService;
	
	@Autowired
	private FrischBestellungService frischBestellungService;
	
	/** Vorgehen beim Vorschlag Berechnen:
	 *  1. Es wird geschaut ob jemand in der Kategorie die Woche eine Bestellung bereits aufgegeben hat
	 *  2. Die erste Bestellung wird als Vorschlag genommen
	 *  3. Muss noch eingebaut werden: Wenn ein Gebinde bereits voll ist z.B. Eichblatt 10x und es wurde parallel Kopfsalat 3x bestellt, dann soll 
	 	   nicht mehr Eichblatt vorgeschlagen werden obwohl dieser als erstes bestellt wurde, sondern Kopfsalat
	 * 
	 * @param kategorie
	 * @return FrischBestellungs Vorschlag der Kategorie für die Woche
	 */
	public FrischBestellung getSuggestionOfCategoryForThisWeek(Kategorie kategorie) {
		List<FrischBestellung> bestellungen = filterAndSortAfterCategory(kategorie);
		if(bestellungen == null) return null;
		return bestellungen.get(0);
	}
	
	
	private List<FrischBestellung> filterAndSortAfterCategory(Kategorie kategorie) {
		List<FrischBestellung> bestellungen = frischBestellungService.findAllOrdersAfterDate(deadlineService.last().getDatum());
		if(bestellungen == null) return null;
		
		// Nur Bestellungen welche die gleiche Kategorie haben, sortiert nach ältesten Timestamp
		// Die älteste Bestellung der Woche gibt dann den Vorschlag vor für das Produkt
		bestellungen = bestellungen.stream().filter(b -> b.getFrischbestand() != null  &&
				b.getFrischbestand().getKategorie() != null &&
				b.getFrischbestand().getKategorie().equals(kategorie))
		.sorted(Comparator.comparing(best -> best.getDatum()))
		.collect(Collectors.toList());
		return bestellungen;
	}
	
	
	 LinkedHashMap<FrischBestand, Float> sortedMap;
	
	public List<DiscrepancyEntity> getDiscrepancyListForMixableCategorie(Kategorie kategorie, int threshold, boolean preferExactGebinde) {
		sortedMap = new LinkedHashMap<>();
		List<FrischBestellung> bestellungen = filterAndSortAfterCategory(kategorie);
		if(bestellungen == null) return null;
		if(!kategorie.isMixable()) 	throw new WrongMethodTypeException("This Method is for mixable orders only! Use Method for non Mixable Categories!");
		List<DiscrepancyEntity> done = new ArrayList<>();
		List<DiscrepancyEntity> toFindSolution = new ArrayList<>();
		HashMap<FrischBestand, Float> amountOrdered = new HashMap<>();
		
		for(FrischBestellung b : bestellungen) {
			if(b.getFrischbestand().getGebindegroesse() <= 1) {
				done.add(new DiscrepancyEntity(UUID.randomUUID().toString(), b.getFrischbestand(), (int) (b.getBestellmenge()), 0f, (float) b.getBestellmenge()));
				continue;
			}
			amountOrdered.merge(b.getFrischbestand(), (float) b.getBestellmenge(), Float::sum);

		}
				Set<FrischBestand> toBeDeleted = new HashSet<>();
				HashMap<FrischBestand, Float> toBeUpdated = new HashMap<>();
				List<DiscrepancyEntity> toBeEdited = new ArrayList<>();
				//Adde alle zu done und entferne aus HashMap, welche genau eine passende Gebindegröße haben oder größer
				for(Map.Entry<FrischBestand, Float> entry : amountOrdered.entrySet()) {
					if(entry.getKey().getGebindegroesse() <= entry.getValue()) {
						if(entry.getValue() % entry.getKey().getGebindegroesse()  == 0) {
							done.add(new DiscrepancyEntity(UUID.randomUUID().toString(), entry.getKey(),(int)( entry.getKey().getGebindegroesse() / entry.getValue()), 0f, entry.getValue()));
							toBeDeleted.add(entry.getKey());
							continue;
						} else {
							double rest = entry.getValue() % entry.getKey().getGebindegroesse() ;
							int amountGebinde = (int)( entry.getValue() / entry.getKey().getGebindegroesse());
							// Später müssen diese hier noch editiert werden!
							toBeEdited.add(new DiscrepancyEntity(UUID.randomUUID().toString(), entry.getKey(), amountGebinde, 0f,entry.getValue()));

							toBeUpdated.put(entry.getKey(), (float)rest);
						}
					}
				}
				amountOrdered.putAll(toBeUpdated);
				
				toBeDeleted.stream().forEach(t -> {
					amountOrdered.remove(t);
				});
				
				//Erneut durchgehen mit rest, um kombinationen zu finden
				
					FrischBestand favourit = null;
					float currentTry = 0;
				
					
				  	amountOrdered.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue())
		            .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
				  	
				  	
				  while(getSolutions(sortedMap)) {
					  sortedMap = new LinkedHashMap<>();
					  	amountOrdered.entrySet()
			            .stream()
			            .sorted(Map.Entry.comparingByValue())
			            .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
				  }
				
				  sortedMap.entrySet().stream().filter(entry -> entry.getValue() == 0);
				  
				  // In sortedMap sind nur noch Rest einträge. Hier kann man nochmal schauen ob man mit Threshold adden kann
				  
				  Optional<FrischBestand> lowestGebinde = sortedMap.keySet().stream().min(Comparator.comparing(fb -> fb.getGebindegroesse()));
				  if(lowestGebinde.isPresent()) {
					  float counter = 0;
					  for(Map.Entry<FrischBestand, Float> entry : sortedMap.entrySet()) {
						  counter = counter + entry.getValue();
					  }
						 threshold = threshold/100;
						 if(counter >= (threshold)) {
							done.add(new DiscrepancyEntity(UUID.randomUUID().toString(),lowestGebinde.get(), 1 , lowestGebinde.get().getGebindegroesse() - counter, counter)) ;
						 } else {
							  for(Map.Entry<FrischBestand, Float> entry : sortedMap.entrySet()) {
								 done.add(new DiscrepancyEntity(UUID.randomUUID().toString(), entry.getKey(), 0, entry.getKey().getGebindegroesse() - entry.getValue(), entry.getValue()));
							  }	 
						 }
				  }
				  return done;
	}
	
	private boolean getSolutions(LinkedHashMap<FrischBestand, Float> sorted) {
		float currentTry = 0;
		HashMap<FrischBestand, Float> newList = new HashMap<>();
		FrischBestand favourit = null;
		boolean roundMissing = false;
		for(Map.Entry<FrischBestand, Float> entry : sorted.entrySet()) {
			
			//Der Erste Eintrag ist der "favourit"
			if(favourit == null) {
				favourit = entry.getKey();
				currentTry = entry.getValue();
				continue;
			}
			
				if(currentTry + entry.getValue() >= favourit.getGebindegroesse()) {
					newList.put(entry.getKey(), currentTry + entry.getValue() % favourit.getGebindegroesse() );
					currentTry = favourit.getGebindegroesse();
					newList.put(favourit, currentTry);
					roundMissing = true;
					break;
				} else {
					currentTry = currentTry + entry.getValue();
					newList.put(entry.getKey(), 0f);
				}
			}
		
		sorted.putAll(newList);
		
		return roundMissing;
	}
	
	
	public DiscrepancyEntity getDiscrepancyForNotMixableCategory(FrischBestellung bestellung, int threshold) {
		List<FrischBestellung> bestellungen = filterAndSortAfterCategory(bestellung.getFrischbestand().getKategorie());
		if(bestellungen == null) return null;
		if(bestellung.getFrischbestand() == null) return null;
		if(bestellung.getFrischbestand().getGebindegroesse() <= 1) return null;
		
		//Falls die Bestellungen nicht aufgefüllt werden sollen.
		// Also z.B. Kategorie Kräuter -> 2x Dill, 1x Petersilie, aber gebindegröße bei beiden 3 dann soll nicht 3x Dill 0x Petersilie.
		// Bei Salat z.B. hingegen schon. Also 6x Eichblatt und 2x Kopfsalat -> Gebindegröße = 10 dann 8x Eichblatt 0x Kopfsalat 
		if(!bestellung.getFrischbestand().getKategorie().isMixable()) {
			 bestellungen = bestellungen.stream().filter(b -> b.getFrischbestand() != null &&
					 b.getFrischbestand().equals(bestellung.getFrischbestand())).collect(Collectors.toList());
			 //wie viel wurde insgesamt von der Ware bestellt
			 double amount = bestellungen.stream()
					 .mapToDouble(b -> b.getBestellmenge())
					 .sum();
			 
			 //Gebindegröße der Ware
			 float gebindegroesse = bestellung.getFrischbestand().getGebindegroesse();
			 DiscrepancyEntity de = new DiscrepancyEntity();
			 de.setId(UUID.randomUUID().toString());
			 de.setBestand(bestellung.getFrischbestand());
			 
			 int sicherZuBestellendeGebinde =  (int) (amount / gebindegroesse);
			 float rest = (float)amount % gebindegroesse;
			 
			 int gesamtZuBestellend = sicherZuBestellendeGebinde;
			 float zuVielZuWenig = 0;
			 threshold = threshold/100;
			 if(rest >= (threshold)) {
				 gesamtZuBestellend = gesamtZuBestellend + 1;
				 zuVielZuWenig = ((float)gesamtZuBestellend * gebindegroesse ) - (float)amount;
			 } else {
				 if(gesamtZuBestellend == 0) {
					 zuVielZuWenig = 0;
				 } else {
					 zuVielZuWenig = -((float)amount - ((float)gesamtZuBestellend * gebindegroesse));
				 }
			 }
			 de.setZuVielzuWenig(zuVielZuWenig);
			 de.setZuBestellendeGebinde(gesamtZuBestellend);
			 de.setGewollteMenge((float) amount);
			 return de;
			 
		} else {
			//Dies ist der Fall, wenn Produkte gemixt werden können wie z.B. Salat um ein Gebindevoll zu bekommen,
			//andere Methode nutzen
			throw new WrongMethodTypeException("This Method is for non mixable orders only! Use Method for Mixable Categories!");
		}
		
		
	}
    
    public List<List<FrischBestellung>> splitBestellungList(List<FrischBestellung> bestellList){
        List<List<FrischBestellung>> bestellungListKategorie = new ArrayList<List<FrischBestellung>>();
//        int length = bestellList.size();
        List<FrischBestellung> kategorie = new ArrayList<FrischBestellung>();
        kategorie.add(bestellList.get(0));
//        double [][] kategorie = new double [length][5];

//        int arraysize = 1;
        for(int i = 1; i < bestellList.size(); i++){
            if(bestellList.get(i).getFrischbestand().getKategorie().getName() == bestellList.get(i-1).getFrischbestand().getKategorie().getName()){
//                arraysize++;

                kategorie.add(bestellList.get(i));
            }
            else{
//                arraysize = 1;
                bestellungListKategorie.add(kategorie);
                for(int j = 0; j < kategorie.size(); j++){
                    System.out.println(kategorie.get(j).getFrischbestand().getName());
                }
                kategorie = new ArrayList<FrischBestellung>();
                kategorie.add(bestellList.get(i));
            }
        }

        bestellungListKategorie.add(kategorie);
        
        return bestellungListKategorie;
    }

    public double[][] VorschlagBerechnen(List<FrischBestellung> kategorie) {
        double[][] liste = new double[kategorie.size()][5];
        double gesamt = 0;
        for (int i = 0; i < kategorie.size(); i++){
            FrischBestellung bestellung = kategorie.get(i);
            liste[i][0] = bestellung.getBestellmenge();
            liste[i][1] = bestellung.getFrischbestand().getGebindegroesse();
            liste[i][2] = 0;
            liste[i][3] = 0;
            liste[i][4] = i;
        }

        //liste[i][0] = Menge
        //liste[i][1] = Gebindegröße
        //liste[i][2] = fehlende menge pro gebinde
        //liste[i][3] = anzahl der gebinde die bestellt werden können
        //liste[i][4] = index
        //gesamt = anzahl aller übrigen nicht im gebinde eingebundenen

        for (int i = 0; i < liste.length; i++){
            // ist ein gebinde voll? ############Noch schauen ob es mehrere volle Gebinde gibnt##########
            if((liste[i][1] - liste[i][0]) <= 0){
                //Menge zieh ich gebindegröße ab
                liste[i][0] -= liste[i][1];
                //Stelle 3 sagt anzahl der gebinde die bestellt werden sollen
                liste[i][3] += 1;
            }
            double uebrig = liste[i][1] - liste [i][0];
            //[i][3] = liste[i][0]
            //System.out.println(uebrig);
            liste[i][2] = uebrig;
            gesamt += liste[i][0];

        }
        //sortieren nach übrigen
        Arrays.sort(liste, (a, b) -> Double.compare(a[2], b[2]));
        //System.out.println(gesamt);
        double optimiert = 0;
        for (int i = 0; i < liste.length; i++){
            if(gesamt - liste[i][1] >= 0){
                liste[i][3]++;
                liste[i][0] = 0;
                liste[i][2] = 1000;
                gesamt -= liste[i][1];
            }
        }
        Arrays.sort(liste, (a, b) -> Double.compare(a[2], b[2]));
        for(double j = 0.05; j<= 0.2; j += 0.05) {
            for (int i = 0; i < liste.length; i++) {
                if (liste[i][1] == gesamt) {
                    gesamt = 0;
                    liste[i][3]++;
                    break;
                }

                //Gebinde größer als gesamt
                if (liste[i][1] - gesamt > 0) {
                    double gesamtP = gesamt + gesamt * j;
                    if (liste[i][1] - gesamtP <= 0) {
                        optimiert = liste[i][1] - gesamt;
                        gesamt = 0;
                        liste[i][3]++;
                        break;
                    }
                }
                //Gebinde kleiner als gesamt
                else {
                    double gesamtP = gesamt - gesamt * j;
                    if (liste[i][1] - gesamtP <= 0) {
                        optimiert = gesamt - liste[i][1];
                        gesamt = 0;
                        liste[i][3]++;
                        break;
                    }
                }
            }
        }
        Arrays.sort(liste, (a, b) -> Double.compare(a[4], b[4]));
        //System.out.println(optimiert);
        //System.out.println(gesamt);


        return liste;
        //##ENDE

    }

}
