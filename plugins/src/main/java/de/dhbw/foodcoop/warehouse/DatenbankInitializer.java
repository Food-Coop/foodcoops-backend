package de.dhbw.foodcoop.warehouse;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;

@Component
public class DatenbankInitializer {

    @Autowired
    private FrischBestandService fbService;
    
    @Autowired
    private EinheitService einheitService;
    
    @Autowired
    private KategorieService kategorieService;
    
    @Autowired
    private BrotBestandService brotService;
    
    @Autowired
    private ProduktService produktService;

    @PostConstruct
    public void init() {
    	
    	//Erstellung aller Einheiten
    	if(einheitService.all().isEmpty() && kategorieService.all().isEmpty() && fbService.all().isEmpty()) {
    		Einheit kg = einheitService.save(new Einheit("Kg"));
    		Einheit St = einheitService.save(new Einheit("Stück"));
    		Einheit Bd = einheitService.save(new Einheit("Bd"));
    		Einheit g = einheitService.save(new Einheit("g"));
    	
    	//Erstellung aller Kategorien
    	
    	Kategorie kartoffeln = kategorieService.save(new Kategorie("Kartoffel", true));
    	Kategorie wurzelgemüse = kategorieService.save(new Kategorie("Wurzelgemüse", false));
    	Kategorie zwiebeln = kategorieService.save(new Kategorie("Zwiebel", false));
    	Kategorie salat = kategorieService.save(new Kategorie("Salat", true));
    	Kategorie petersilie = kategorieService.save(new Kategorie("Petersilie", false));
    	Kategorie spinat = kategorieService.save(new Kategorie("Spinat", true));
    	Kategorie gemüse = kategorieService.save(new Kategorie("Gemüse", false));
    	Kategorie kreuzblüten = kategorieService.save(new Kategorie("Kreuzblüten", false));
    	Kategorie zitrusfrüchte = kategorieService.save(new Kategorie("Zitrusfrucht", false));
    	Kategorie frucht = kategorieService.save(new Kategorie("Frucht", false));
    	Kategorie apfel = kategorieService.save(new Kategorie("Apfel", true));
    	Kategorie pilze = kategorieService.save(new Kategorie("Pilze", false));
    	Kategorie eier = kategorieService.save(new Kategorie("Eier", false));
    	
    	Kategorie Backmittel = kategorieService.save(new Kategorie("Backmittel", false));
    	Kategorie Essigoel = kategorieService.save(new Kategorie("Essig-Öl", false));
    	Kategorie reis = kategorieService.save(new Kategorie("Reis", false));
    	Kategorie Bratlinge = kategorieService.save(new Kategorie("Backmittel", false));
    	Kategorie GriesGetreide = kategorieService.save(new Kategorie("Gries-Getreide-Mehl", false));
    	Kategorie Sueskram = kategorieService.save(new Kategorie("Süßkram-Knabberzeug", false));
    	Kategorie Huelsenfruechte = kategorieService.save(new Kategorie("Hülsenfrüchte", false));
    	Kategorie Tee = kategorieService.save(new Kategorie("Tee", false));
    	Kategorie fisch = kategorieService.save(new Kategorie("Fischkonserven", false));
   
    	Kategorie muesli = kategorieService.save(new Kategorie("Müsli-Crunchy", false));
    	Kategorie suessmittel = kategorieService.save(new Kategorie("Süssmittel", false));
    	Kategorie pudding = kategorieService.save(new Kategorie("Pudding", false));
    	Kategorie wuerze = kategorieService.save(new Kategorie("Würze", false));
    	Kategorie Pastasosen = kategorieService.save(new Kategorie("Pasta Soßen-Pesto", false));
    	Kategorie Tomaten = kategorieService.save(new Kategorie("Tomatenprodukte", false));
    	Kategorie Nudeln = kategorieService.save(new Kategorie("Nudeln", false));
    	Kategorie brotaufstrich = kategorieService.save(new Kategorie("Brotaufstriche", false));
    	Kategorie milch = kategorieService.save(new Kategorie("Milch-Milchersatz", false));
    	Kategorie gemuese = kategorieService.save(new Kategorie("Gemüse-Obstkonserven", false));
    	
    	
    	//Erstellung aller FrischBestände
    	
    	fbService.save(new FrischBestand("Kartoffeln fk", true, "D", 12.50f, kg , kartoffeln, 1.60f));
    	fbService.save(new FrischBestand("Kartoffeln vfk", true, "D", 12.50f, kg, kartoffeln, 1.60f));
    	fbService.save(new FrischBestand("Kartoffeln ml", true, "D", 12.50f, kg, kartoffeln, 1.60f));
    		
    	fbService.save(new FrischBestand("Zwiebeln gelb", true, "D", 10f, kg, zwiebeln, 2.70f));
    		
    	fbService.save(new FrischBestand("Möhren", true, "D", 12.50f, kg, wurzelgemüse, 1.80f));
    	fbService.save(new FrischBestand("Rote Bete", true, "D", 5f, kg, wurzelgemüse, 2.90f));
    	fbService.save(new FrischBestand("Pastinaken", true, "D", 5f, kg, wurzelgemüse, 3.20f));
    	fbService.save(new FrischBestand("Knollensellerie", true, "F", 5f, kg, wurzelgemüse, 2.90f));
    	fbService.save(new FrischBestand("Staudensellerie", true, "E", 1f, kg, wurzelgemüse, 5.20f));
    		
    	fbService.save(new FrischBestand("Batavia grün", true, "D", 8f, St, salat, 2f));
    	fbService.save(new FrischBestand("Eichblatt grün", true, "D", 8f, St, salat, 2f));
    	fbService.save(new FrischBestand("Eichblatt rot", true, "D", 8f, St, salat, 2f));
    	fbService.save(new FrischBestand("Kopfsalat grün", true, "D", 8f, St, salat, 2f));
    		
       	fbService.save(new FrischBestand("Petersilie glatt", true, "I", 1f, Bd, petersilie, 1.60f));
       	fbService.save(new FrischBestand("Petersilie kraus", true, "D", 1f, Bd, petersilie, 2f));
    		
       	fbService.save(new FrischBestand("Lauch", true, "D", 5f, kg, gemüse, 4.60f));
       	fbService.save(new FrischBestand("Fenchel", true, "I", 5f, kg, gemüse, 3.80f));
       	fbService.save(new FrischBestand("Spinat", true, "D", 3f, kg, spinat, 7f));
       	fbService.save(new FrischBestand("Mangold", true, "I", 6f, kg, spinat, 3.80f));
       		
       	fbService.save(new FrischBestand("Chinakohl", true, "E", 8f, kg, kreuzblüten, 4.30f));
       	fbService.save(new FrischBestand("Blumenkohl", true, "I", 1f, kg, kreuzblüten, 4.20f));
       	fbService.save(new FrischBestand("Brokkoli", true, "E", 5f, kg, kreuzblüten, 3.90f));
       	fbService.save(new FrischBestand("Spitzkohl", true, "E", 7f, kg, kreuzblüten, 3.70f));
       	fbService.save(new FrischBestand("Kohlrabi", true, "I", 14f, St, kreuzblüten, 1.60f));
       	fbService.save(new FrischBestand("Radieschen", true, "D", 1f, Bd, kreuzblüten, 2.50f));
       		
       	fbService.save(new FrischBestand("Tomaten", true, "E", 5f, kg, gemüse, 3.80f));
    	fbService.save(new FrischBestand("Gurken", true, "E", 12f, St, gemüse, 1.60f));
       	fbService.save(new FrischBestand("Zucchini", true, "E", 6f, kg, gemüse, 3.60f));
       	fbService.save(new FrischBestand("Paprika rot", true, "E", 5f, kg, gemüse, 6.20f));
       	fbService.save(new FrischBestand("Süßkartoffeln", true, "D", 6f, kg, gemüse, 5.20f));
       		
       	fbService.save(new FrischBestand("Clementinen", true, "E", 6f, kg, zitrusfrüchte, 2.90f));
       	fbService.save(new FrischBestand("Grapefruit", true, "E", 1f, St, zitrusfrüchte, 1.30f));
       	fbService.save(new FrischBestand("Orangen", true, "E", 9f, kg, zitrusfrüchte, 2.80f));
       	fbService.save(new FrischBestand("Saftorangen", true, "E", 10f, kg, zitrusfrüchte, 2.30f));
       	fbService.save(new FrischBestand("Kiwi grün", true, "I", 1f, St, frucht, 0.50f));
       	fbService.save(new FrischBestand("Zitronen", true, "I", 1f, kg, zitrusfrüchte, 0.60f));
       		
       	fbService.save(new FrischBestand("Äpfel Topaz", true, "D", 10f, kg, apfel, 3.60f));
       	fbService.save(new FrischBestand("Äpfel Boskoop", true, "D", 10f, kg, apfel, 3.60f));
       	fbService.save(new FrischBestand("Äpfel Elstar", true, "D", 10f, kg, apfel, 3.60f));
       		
       	fbService.save(new FrischBestand("Champignons", true, "D", 2f, kg, pilze, 9.80f));
       	fbService.save(new FrischBestand("Kräuterseitlinge", true, "D", 1f, kg, pilze, 22f));
       	fbService.save(new FrischBestand("Knoblauch", true, "Ar", 1f, kg, gemüse, 12f));
       	fbService.save(new FrischBestand("Eier", true, "D", 1f, St, eier, 0.55f));
       	
       	// Erstellung von Brot
       	brotService.save(new BrotBestand("11", "Demeter-Dinkellaib", true, 500, 0));
       	brotService.save(new BrotBestand("12", "Dinkel-Wurzelbrot", true, 500, 0));
       	brotService.save(new BrotBestand("14", "Schweizer Kruste", true, 750, 0));
       	brotService.save(new BrotBestand("15", "Kamut-Brot", true, 750, 0));
       	brotService.save(new BrotBestand("16", "Saftbrot ESSENER Art", true, 750, 0));
       	brotService.save(new BrotBestand("20", "Genetztes Dinkelbrotb", true, 750, 0));
       	brotService.save(new BrotBestand("22", "Dinkel Saatenbrot", true, 500, 0));
       	brotService.save(new BrotBestand("23", "Hafer-Dinkelbrot", true, 500, 0));
       	brotService.save(new BrotBestand("27", "Kürbisbrot mit Hirse", true, 500, 0));
       	brotService.save(new BrotBestand("28", "Möhren-Kürbisbrot", true, 750, 0));
       	brotService.save(new BrotBestand("30", "Sonnenblumenbrot", true, 750, 0));
       	brotService.save(new BrotBestand("32", "Mischbrot", true, 750, 0));
       	brotService.save(new BrotBestand("33", "Mehrkornbrot", true, 750, 0));
       	brotService.save(new BrotBestand("34", "Blankenlocher", true, 500, 0));
       	brotService.save(new BrotBestand("35", "Reichenbacher", true, 1000, 0));
       	brotService.save(new BrotBestand("37", "Kasten-Weissbrot", true, 500, 0));
       	brotService.save(new BrotBestand("40", "Hausbrot", true, 750, 0));
       	brotService.save(new BrotBestand("41", "Walnußbrot", true, 500, 0));
       	brotService.save(new BrotBestand("50", "Roggenbrot", true, 750, 0));
       	brotService.save(new BrotBestand("56", "Rhein Schwarzbrot", true, 500, 0));
       	brotService.save(new BrotBestand("61", "Brauer Kruste", true, 500, 0));
       	brotService.save(new BrotBestand("71", "Kleine Hirse", true, 500, 0));
       	brotService.save(new BrotBestand("77", "Roggenmischbrot", true, 500, 0));
       	brotService.save(new BrotBestand("92", "Low-Carb-Brot", true, 320, 0));
       	brotService.save(new BrotBestand("120", "Dinkelbrötchen Vollkorn", true, 0, 0));
       	brotService.save(new BrotBestand("124", "Dinkelbaguette Vollkorn", true, 0, 0));
       	brotService.save(new BrotBestand("125", "Brötchen-Rad, verziert", true, 0, 0));
       	brotService.save(new BrotBestand("130", "Mohnbrötchen", true, 0, 0));
       	brotService.save(new BrotBestand("131", "Sonnenblumenbrötchen", true, 0, 0));
       	brotService.save(new BrotBestand("132", "Sesambrötchen", true, 0, 0));
       	
       	brotService.save(new BrotBestand("133", "Kürbiskernbrötchen", true, 0, 0));
       	brotService.save(new BrotBestand("135", "Thomasbrötchen", true, 0, 0));
       	brotService.save(new BrotBestand("139", "Fladen Weizen-Vollkorn", true, 0, 0));
       	brotService.save(new BrotBestand("146", "Dinkel-Krusti", true, 0, 0));
       	brotService.save(new BrotBestand("147", "Dinkel-Bauernbaguette", true, 0, 0));
       	brotService.save(new BrotBestand("150", "Elsässer Doppelweck", true, 0, 0));
       	brotService.save(new BrotBestand("151", "Roggenweck", true, 0, 0));
       	brotService.save(new BrotBestand("156", "Dinkel-Kamut-Panini", true, 0, 0));
       	brotService.save(new BrotBestand("157", "Dinkel-Kamut Seele", true, 0, 0));
       	brotService.save(new BrotBestand("158", "Dinkel-Kamut Ciabatta", true, 0, 0));
       	brotService.save(new BrotBestand("159", "Dinkel-Kamut Ciabatta Olive", true, 0, 0));
       	brotService.save(new BrotBestand("370", "Berliner", false, 0, 0));
       	brotService.save(new BrotBestand("331", "Faschingsmaske", false, 0, 0));
       	brotService.save(new BrotBestand("332", "Osterhase", true, 0, 0));
       	brotService.save(new BrotBestand("339", "Osterbrot", true, 0, 0));
       	brotService.save(new BrotBestand("334", "Martinsgans", false, 0, 0));
       	brotService.save(new BrotBestand("335", "Dambedei", false, 0, 0));
       	brotService.save(new BrotBestand("336", "Neujahrsbr 110g", false, 110, 0));
       	brotService.save(new BrotBestand("337", "Neujahrsbr 600g", true, 600, 0));
       	brotService.save(new BrotBestand("432", "D.-Schoko-Herz Zartb.", true, 18, 0));
       	brotService.save(new BrotBestand("433", "D.-Mandel-Stern Zartb.", true, 15, 0));
       	brotService.save(new BrotBestand("434", "Dinkel-Mandel-Lebk", true, 18, 0));
       	brotService.save(new BrotBestand("463", "Honig-Lebkuchen Platte", false, 0, 0));
       	brotService.save(new BrotBestand("464", "Honig-Lebkuchen Herz", false, 0, 0));
    	brotService.save(new BrotBestand("467", "Hutzelbrot", false, 0, 0));
    	
       	brotService.save(new BrotBestand("160", "Dinkellaugen-Ring Vollkorn", true, 0, 0));
       	brotService.save(new BrotBestand("162", "Dinkellaugen-Brezel ab 5 St", true, 0, 0));
       	brotService.save(new BrotBestand("163", "Laugen-Brezel Salz ab 5 St", true, 0, 0));
       	brotService.save(new BrotBestand("164", "Schlemmerschleife", true, 0, 0));
       	brotService.save(new BrotBestand("165", "Laugen-Brezel Sesam ab 5 St", true, 0, 0));
       	brotService.save(new BrotBestand("166", "Käse-Laugenweck", true, 0, 0));
       	brotService.save(new BrotBestand("167", "Laugenknoten", true, 0, 0));
       	brotService.save(new BrotBestand("168", "Laugenstange", true, 0, 0));
       	brotService.save(new BrotBestand("169", "Laugenbaguette", true, 0, 0));
       	brotService.save(new BrotBestand("171", "Baguette Tradition", true, 0, 0));
       	brotService.save(new BrotBestand("172", "Baguette Campaillou", true, 0, 0));
       	brotService.save(new BrotBestand("175", "Petite", true, 0, 0));
       	brotService.save(new BrotBestand("201", "Gemüsekuchen rund 12 St", true, 0, 0));
    	brotService.save(new BrotBestand("202", "Zwiebelkuchen", false, 0, 0));
    	
       	brotService.save(new BrotBestand("203", "Zwiebelkuchen m. Speck", false, 0, 0));
       	brotService.save(new BrotBestand("211", "Pizza-Schnecke", true, 0, 0));
       	brotService.save(new BrotBestand("214", "Napoli", true, 0, 0));
       	brotService.save(new BrotBestand("215", "Spinaci", true, 0, 0));
       	brotService.save(new BrotBestand("252", "6-Korn Laugencroissant", true, 0, 0));
       	brotService.save(new BrotBestand("290", "Schokofranzbrötchen vegan", true, 0, 0));
       	brotService.save(new BrotBestand("301", "Franzbrötchen (Zimtschnecke)", true, 0, 0));
    	brotService.save(new BrotBestand("302", "Rosinenbrötchen", true, 0, 0));
    	
       	brotService.save(new BrotBestand("303", "Dinkel-Apfelschnecke", true, 0, 0));
       	brotService.save(new BrotBestand("304", "Dinkel-Mohnschnecke", true, 0, 0));
       	brotService.save(new BrotBestand("305", "Topfen/Quarktasche", true, 0, 0));
       	brotService.save(new BrotBestand("306", "Kirschplunder vegan", true, 0, 0));
       	brotService.save(new BrotBestand("308", "Apfeltasche", true, 0, 0));
       	brotService.save(new BrotBestand("309", "Rosinenschnecke la Creme", true, 0, 0));
    	brotService.save(new BrotBestand("310", "Schoko Croissant", true, 0, 0));
    	
       	brotService.save(new BrotBestand("313", "Dinkel-Croissant hell", true, 0, 0));
       	brotService.save(new BrotBestand("315", "Croissant französisch", true, 0, 0));
       	brotService.save(new BrotBestand("322", "Stutenseer Zopf", true, 0, 0));
       	brotService.save(new BrotBestand("323", "Mohn Zopf", true, 0, 0));
       	brotService.save(new BrotBestand("324", "Dinkel Mohn Tartes", true, 0, 0));
       	brotService.save(new BrotBestand("325", "Dinkel Apfel Tartes", true, 0, 0));
    	brotService.save(new BrotBestand("326", "Dinkel Käse Tartes", true, 0, 0));
    	
       	brotService.save(new BrotBestand("327", "Dinkel Gemüse Tartes", true, 0, 0));
       	brotService.save(new BrotBestand("328", "Quarkbällchen ab 3 St", true, 0, 0));
       	brotService.save(new BrotBestand("329", "Amerikaner", true, 0, 0));
       	brotService.save(new BrotBestand("330", "Auszogne", false, 0, 0));
       	brotService.save(new BrotBestand("341", "Apfel-Blech-Kuchen", true, 0, 0));
       	brotService.save(new BrotBestand("342", "Schoko-Kirsch-Kuchen", true, 0, 0));
    	brotService.save(new BrotBestand("343", "Schwarze Johannisbeer-Kuchen", true, 0, 0));
    	
       	brotService.save(new BrotBestand("344", "Käsekuchen", true, 0, 0));
       	brotService.save(new BrotBestand("351", "Rhabarberkuchen", true, 0, 0));
       	brotService.save(new BrotBestand("352", "Zwetschgenkuchen", false, 0, 0));
       	brotService.save(new BrotBestand("353", "Mohnkuchen", true, 0, 0));
       	brotService.save(new BrotBestand("401", "Amaranth-Taler vegan", true, 18, 0));
       	brotService.save(new BrotBestand("402", "Linzer-Torte", true, 0, 0));
    	brotService.save(new BrotBestand("405", "Dinkel-Mandel-Zungen", true, 16, 0));
    	
       	brotService.save(new BrotBestand("406", "Dinkel-Nougat-Ring", true, 16, 0));
       	brotService.save(new BrotBestand("407", "Schoko-Peanuts Vollm.", true, 18, 0));
       	brotService.save(new BrotBestand("420", "Mürbegebäch Nuss", false, 4, 0));
       	brotService.save(new BrotBestand("421", "Mürbegebäck Frucht", false, 4, 0));
       	brotService.save(new BrotBestand("422", "Kernbeißer", true, 12, 0));

       	
       	//Lagerware
       	produktService.save(new Produkt("Backhefe 7g","Biovegan Meister Backhefe", Backmittel, new Lagerbestand(g, null, null), 2f));
    	}
    }
}