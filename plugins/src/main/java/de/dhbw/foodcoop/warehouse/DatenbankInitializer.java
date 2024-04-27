package de.dhbw.foodcoop.warehouse;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.application.admin.ConfigurationService;
import de.dhbw.foodcoop.warehouse.application.brot.BrotBestandService;
import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;
import de.dhbw.foodcoop.warehouse.application.frischbestellung.FrischBestandService;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.application.lager.KategorieService;
import de.dhbw.foodcoop.warehouse.application.lager.ProduktService;
import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.ConfigurationEntity;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.utils.ConstantsUtils;
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
    
    @Autowired
    private ConfigurationService cfgService;
    
    @Autowired
    private DeadlineService deadlineService;
    

    @PostConstruct
    public void init() {

    	
    	TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
    	if(cfgService.getConfig().isEmpty()) {
    		cfgService.updateConfig(new ConfigurationEntity(ConstantsUtils.EMAIL_TEXT_EINKAUF_UEBERSICHT, ConstantsUtils.THRESHOLD, 8.0, ConstantsUtils.EMAIL_TEXT_EINKAUFSMANAGEMENT, ConstantsUtils.EMAIL_TEXT_LAGERMEISTER));
    	}
    	//Erstellung aller Einheiten
    	if(einheitService.all().isEmpty() && kategorieService.all().isEmpty() && fbService.all().isEmpty() && produktService.all().isEmpty() && brotService.all().isEmpty() && deadlineService.all().isEmpty()) {

    		deadlineService.coldStart(new Deadline(UUID.randomUUID().toString(), DeadlineService.germanDaysOfWeekReversed.get(LocalDateTime.now().getDayOfWeek()), Time.valueOf("23:59:00"), LocalDateTime.now()));
    		deadlineService.coldStart(new Deadline(UUID.randomUUID().toString(), DeadlineService.germanDaysOfWeekReversed.get(LocalDateTime.now().plusDays(1).getDayOfWeek()), Time.valueOf("23:59:00"), LocalDateTime.now().plusSeconds(10)));
    		
    		Einheit kg = einheitService.save(new Einheit("Kg"));
    		Einheit St = einheitService.save(new Einheit("Stück"));
    		Einheit Bd = einheitService.save(new Einheit("Bd"));
    		Einheit g = einheitService.save(new Einheit("g"));
    		Einheit l = einheitService.save(new Einheit("l"));
    		Einheit p = einheitService.save(new Einheit("Packung"));
    		Einheit k = einheitService.save(new Einheit("Konserve"));
    		Einheit ml = einheitService.save(new Einheit("ml"));
    	
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
    	int counter = 1000;
    	fbService.save(new FrischBestand(counter++ + "", "Kartoffeln fk", true, "D", 12.50f, kg , kartoffeln, 1.60f, "e.E.", false));
    	fbService.save(new FrischBestand(counter++ + "","Kartoffeln vfk", true, "D", 12.50f, kg, kartoffeln, 1.60f, "e.E.", false));
    	fbService.save(new FrischBestand(counter++ + "","Kartoffeln ml", true, "D", 12.50f, kg, kartoffeln, 1.60f, "e.E.", false));
    		
    	fbService.save(new FrischBestand(counter++ + "","Zwiebeln gelb", true, "D", 10f, kg, zwiebeln, 2.70f, "DB", false));
    		
    	fbService.save(new FrischBestand(counter++ + "","Möhren", true, "D", 12.50f, kg, wurzelgemüse, 1.80f, "e.E.", false));
    	fbService.save(new FrischBestand(counter++ + "","Rote Bete", true, "D", 5f, kg, wurzelgemüse, 2.90f, "DB", false));
    	fbService.save(new FrischBestand(counter++ + "","Pastinaken", true, "D", 5f, kg, wurzelgemüse, 3.20f, "e.E.", false));
    	fbService.save(new FrischBestand(counter++ + "","Knollensellerie", true, "F", 5f, kg, wurzelgemüse, 2.90f, "DB", false));
    	fbService.save(new FrischBestand(counter++ + "","Staudensellerie", true, "E", 1f, kg, wurzelgemüse, 5.20f, "EG", false));
    		
    	fbService.save(new FrischBestand(counter++ + "","Batavia grün", true, "D", 8f, St, salat, 2f, "DB", false));
    	fbService.save(new FrischBestand(counter++ + "","Eichblatt grün", true, "D", 8f, St, salat, 2f, "DB", false));
    	fbService.save(new FrischBestand(counter++ + "","Eichblatt rot", true, "D", 8f, St, salat, 2f, "DB", false));
    	fbService.save(new FrischBestand(counter++ + "","Kopfsalat grün", true, "D", 8f, St, salat, 2f, "DB", false));
    		
       	fbService.save(new FrischBestand(counter++ + "","Petersilie glatt", true, "I", 1f, Bd, petersilie, 1.60f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Petersilie kraus", true, "D", 1f, Bd, petersilie, 2f, "DD", false));
    		
       	fbService.save(new FrischBestand(counter++ + "","Lauch", true, "D", 5f, kg, gemüse, 4.60f, "DB", false));
       	fbService.save(new FrischBestand(counter++ + "","Fenchel", true, "I", 5f, kg, gemüse, 3.80f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Spinat", true, "D", 3f, kg, spinat, 7f, "DD", false));
       	fbService.save(new FrischBestand(counter++ + "","Mangold", true, "I", 6f, kg, spinat, 3.80f, "EG", false));
       		
       	fbService.save(new FrischBestand(counter++ + "","Chinakohl", true, "E", 8f, kg, kreuzblüten, 4.30f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Blumenkohl", true, "I", 1f, kg, kreuzblüten, 4.20f, "EG", true));
       	fbService.save(new FrischBestand(counter++ + "","Brokkoli", true, "E", 5f, kg, kreuzblüten, 3.90f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Spitzkohl", true, "E", 7f, kg, kreuzblüten, 3.70f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Kohlrabi", true, "I", 14f, St, kreuzblüten, 1.60f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Radieschen", true, "D", 1f, Bd, kreuzblüten, 2.50f, "DD", false));
       		
       	fbService.save(new FrischBestand(counter++ + "","Tomaten", true, "E", 5f, kg, gemüse, 3.80f, "EG", false));
    	fbService.save(new FrischBestand(counter++ + "","Gurken", true, "E", 12f, St, gemüse, 1.60f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Zucchini", true, "E", 6f, kg, gemüse, 3.60f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Paprika rot", true, "E", 5f, kg, gemüse, 6.20f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Süßkartoffeln", true, "D", 6f, kg, gemüse, 5.20f, "DB", false));
       		
       	fbService.save(new FrischBestand(counter++ + "","Clementinen", true, "E", 6f, kg, zitrusfrüchte, 2.90f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Grapefruit", true, "E", 1f, St, zitrusfrüchte, 1.30f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Orangen", true, "E", 9f, kg, zitrusfrüchte, 2.80f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Saftorangen", true, "E", 10f, kg, zitrusfrüchte, 2.30f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Kiwi grün", true, "I", 1f, St, frucht, 0.50f, "EG", false));
       	fbService.save(new FrischBestand(counter++ + "","Zitronen", true, "I", 1f, kg, zitrusfrüchte, 0.60f, "EG", false));
       		
       	fbService.save(new FrischBestand(counter++ + "","Äpfel Topaz", true, "D", 10f, kg, apfel, 3.60f, "DB", false));
       	fbService.save(new FrischBestand(counter++ + "","Äpfel Boskoop", true, "D", 10f, kg, apfel, 3.60f, "DB", false));
       	fbService.save(new FrischBestand(counter++ + "","Äpfel Elstar", true, "D", 10f, kg, apfel, 3.60f, "DB", false));
       		
       	fbService.save(new FrischBestand(counter++ + "","Champignons", true, "D", 2f, kg, pilze, 9.80f, "DB", false));
       	fbService.save(new FrischBestand(counter++ + "","Kräuterseitlinge", true, "D", 1f, kg, pilze, 22f, "DD", false));
       	fbService.save(new FrischBestand(counter++ + "","Knoblauch", true, "Ar", 1f, kg, gemüse, 12f, "EG", true));
       	fbService.save(new FrischBestand(counter++ + "","Eier", true, "D", 1f, St, eier, 0.55f, "DB", false));
       	
       	// Erstellung von Brot
       	brotService.save(new BrotBestand("11", "Demeter-Dinkellaib", true, 500, 3.3f));
       	brotService.save(new BrotBestand("12", "Dinkel-Wurzelbrot", true, 500, 3.1f));
       	brotService.save(new BrotBestand("14", "Schweizer Kruste", true, 750, 3.7f));
       	brotService.save(new BrotBestand("15", "Kamut-Brot", true, 750, 4.85f));
       	brotService.save(new BrotBestand("16", "Saftbrot ESSENER Art", true, 750, 4.05f));
       	brotService.save(new BrotBestand("20", "Genetztes Dinkelbrotb", true, 750, 3.95f));
       	brotService.save(new BrotBestand("22", "Dinkel Saatenbrot", true, 500, 3.25f));
       	brotService.save(new BrotBestand("23", "Hafer-Dinkelbrot", true, 500, 3.4f));
       	brotService.save(new BrotBestand("27", "Kürbisbrot mit Hirse", true, 500, 3.35f));
       	brotService.save(new BrotBestand("28", "Möhren-Kürbisbrot", true, 750, 4.25f));
       	brotService.save(new BrotBestand("30", "Sonnenblumenbrot", true, 750, 4.05f));
       	brotService.save(new BrotBestand("32", "Mischbrot", true, 750, 3.25f));
       	brotService.save(new BrotBestand("33", "Mehrkornbrot", true, 750, 3.85f));
       	brotService.save(new BrotBestand("34", "Blankenlocher", true, 500, 2.5f));
       	brotService.save(new BrotBestand("35", "Reichenbacher", true, 1000, 4.5f));
       	brotService.save(new BrotBestand("37", "Kasten-Weissbrot", true, 500, 2.65f));
       	brotService.save(new BrotBestand("40", "Hausbrot", true, 750, 3.25f));
       	brotService.save(new BrotBestand("41", "Walnußbrot", true, 500, 3.65f));
       	brotService.save(new BrotBestand("50", "Roggenbrot", true, 750, 3.4f));
       	brotService.save(new BrotBestand("56", "Rhein. Schwarzbrot", true, 500, 2.45f));
       	brotService.save(new BrotBestand("61", "Brauer KRUSTE", true, 500, 2.85f));
       	brotService.save(new BrotBestand("71", "Kleine Hirse", true, 500, 3f));
       	brotService.save(new BrotBestand("77", "Roggenmischbrot", true, 500, 2.7f));
      //  brotService.save(new BrotBestand("91", "Backmischung SOWIE LowCarb", true, 250, 6.2f));
       	brotService.save(new BrotBestand("92", "Low-Carb-Brot", true, 320, 4.85f));
        brotService.save(new BrotBestand("95", "Glutenfreies Brot", true, 380, 2.75f));
       	brotService.save(new BrotBestand("120", "Dinkelbrötchen Vollkorn", true, 0, 0.7f));
       	brotService.save(new BrotBestand("124", "Dinkelbaguette Vollkorn", true, 0, 2f));
       	brotService.save(new BrotBestand("125", "Brötchen-Rad, verziert", true, 0, 7.7f));
       	brotService.save(new BrotBestand("130", "Mohnbrötchen", true, 0, 0.6f));
       	brotService.save(new BrotBestand("131", "Sonnenblumenbrötchen", true, 0, 0.6f));
       	brotService.save(new BrotBestand("132", "Sesambrötchen", true, 0, 0.6f));
       	
       	brotService.save(new BrotBestand("133", "Kürbiskernbrötchen", true, 0, 0.6f));
       	brotService.save(new BrotBestand("135", "Thomasbrötchen", true, 0, 0.55f));
       	brotService.save(new BrotBestand("139", "Fladen Weizen-Vollkorn", true, 0, 1f));
        brotService.save(new BrotBestand("142", "Sesambaguette, Vollkorn", true, 0, 2.2f));
       	brotService.save(new BrotBestand("146", "Dinkel-Krusti", true, 0, 1.2f));
       	brotService.save(new BrotBestand("147", "Dinkel-Bauernbaguette", true, 0, 2.35f));
       	brotService.save(new BrotBestand("150", "Elsässer Doppelweck", true, 0, 0.85f));
       	brotService.save(new BrotBestand("151", "Roggenweck", true, 0, 0.6f));
       	brotService.save(new BrotBestand("156", "Dinkel-Kamut-Panini", true, 0, 1.1f));
       	brotService.save(new BrotBestand("157", "Dinkel-Kamut Seele", true, 0, 1.3f));
       	brotService.save(new BrotBestand("158", "Dinkel-Kamut Ciabatta", true, 0, 2.35f));
       	brotService.save(new BrotBestand("159", "Dinkel-Kamut Ciabatta Olive", true, 0, 2.65f));
        
       	brotService.save(new BrotBestand("370", "Berliner", false, 0, 1.55f));
       	brotService.save(new BrotBestand("331", "Faschingsmaske", false, 0, 1.55f));
       	brotService.save(new BrotBestand("332", "Osterhase", true, 0, 1.55f));
       	brotService.save(new BrotBestand("339", "Osterbrot", true, 0, 3.5f));
       	brotService.save(new BrotBestand("334", "Martinsgans", false, 0, 1.7f));
       	brotService.save(new BrotBestand("335", "Dambedei", false, 0, 1.7f));
       	brotService.save(new BrotBestand("336", "Neujahrsbr 110g", false, 110, 1.7f));
       	brotService.save(new BrotBestand("337", "Neujahrsbr 600g", true, 600, 10.4f));
        brotService.save(new BrotBestand("463", "Honig-Lebkuchen Platte", false, 0, 1.7f));
       	brotService.save(new BrotBestand("464", "Honig-Lebkuchen Herz", false, 0, 1.7f));
    	brotService.save(new BrotBestand("467", "Hutzelbrot", false, 0, 4.55f));
            
       	/* brotService.save(new BrotBestand("432", "D.-Schoko-Herz Zartb.", true, 18, 0));
       	brotService.save(new BrotBestand("433", "D.-Mandel-Stern Zartb.", true, 15, 0));
       	brotService.save(new BrotBestand("434", "Dinkel-Mandel-Lebk", true, 18, 0));
        brotService.save(new BrotBestand("329", "Amerikaner", true, 0, 0));
       	brotService.save(new BrotBestand("330", "Auszogne", false, 0, 0));
        brotService.save(new BrotBestand("420", "Mürbegebäck Nuss", false, 4, 0));
       	brotService.save(new BrotBestand("421", "Mürbegebäck Frucht", false, 4, 0));
        brotService.save(new BrotBestand("344", "Käsekuchen", true, 0, 0)); */
    	
       	brotService.save(new BrotBestand("160", "Dinkellaugen-Ring Vollkorn", true, 0, 0.9f));
       	brotService.save(new BrotBestand("162", "Dinkellaugen-Brezel ab 5 St", true, 0, 0.95f));
       	brotService.save(new BrotBestand("163", "Laugen-Brezel Salz ab 5 St", true, 0, 1f));
       	brotService.save(new BrotBestand("164", "Schlemmerschleife", true, 0, 1.85f));
       	brotService.save(new BrotBestand("165", "Laugen-Brezel Sesam ab 5 St", true, 0, 1f));
       	brotService.save(new BrotBestand("166", "Käse-Laugenweck", true, 0, 1.3f));
       	brotService.save(new BrotBestand("167", "Laugenknoten", true, 0, 0.95f));
       	brotService.save(new BrotBestand("168", "Laugenstange", true, 0, 0.95f));
       	brotService.save(new BrotBestand("169", "Laugenbaguette", true, 0, 2.55f));
       	brotService.save(new BrotBestand("171", "Baguette Tradition", true, 0, 2.2f));
       	brotService.save(new BrotBestand("172", "Baguette Campaillou", true, 0, 2.2f));
       	brotService.save(new BrotBestand("175", "Petite", true, 0, 0.85f));
       	brotService.save(new BrotBestand("201", "Gemüsekuchen rund 12 St", true, 0, 25.45f));
    	brotService.save(new BrotBestand("202", "Zwiebelkuchen", false, 0, 22.5f));
    	
       	brotService.save(new BrotBestand("203", "Zwiebelkuchen m. Speck", false, 0, 23.75f));
       	brotService.save(new BrotBestand("211", "Pizza-Schnecke", true, 0, 2f));
       	brotService.save(new BrotBestand("214", "Napoli", true, 0, 2.2f));
       	brotService.save(new BrotBestand("215", "Spinaci", true, 0, 2.2f));
       	brotService.save(new BrotBestand("252", "6-Korn Laugencroissant", true, 0, 1.55f));
       	brotService.save(new BrotBestand("290", "Schokofranzbrötchen vegan", true, 0, 2f));
       	brotService.save(new BrotBestand("301", "Franzbrötchen (Zimtschnecke)", true, 0, 1.75f));
    	brotService.save(new BrotBestand("302", "Rosinenbrötchen", true, 0, 1f));
    	
       	brotService.save(new BrotBestand("303", "Dinkel-Apfelschnecke", true, 0, 2.1f));
       	brotService.save(new BrotBestand("304", "Dinkel-Mohnschnecke", true, 0, 2.15f));
       	brotService.save(new BrotBestand("305", "Topfen/Quarktasche", true, 0, 2.15f));
       	brotService.save(new BrotBestand("306", "Kirschplunder vegan", true, 0, 2.2f));
       	brotService.save(new BrotBestand("308", "Apfeltasche", true, 0, 2.2f));
       	brotService.save(new BrotBestand("309", "Rosinenschnecke la Créme", true, 0, 1.85f));
    	brotService.save(new BrotBestand("310", "Schoko Croissant", true, 0, 1.55f));
    	
       	brotService.save(new BrotBestand("313", "Dinkel-Croissant hell", true, 0, 1.65f));
       	brotService.save(new BrotBestand("315", "Croissant französisch", true, 0, 1.5f));
       	brotService.save(new BrotBestand("322", "Stutenseer Zopf", true, 0, 3.25f));
       	brotService.save(new BrotBestand("323", "Mohn Zopf", true, 0, 3.5f));
       	brotService.save(new BrotBestand("324", "Dinkel Mohn Tartes", true, 0, 2.5f));
       	brotService.save(new BrotBestand("325", "Dinkel Apfel Tartes", true, 0, 2.5f));
    	brotService.save(new BrotBestand("326", "Dinkel Käse Tartes", true, 0, 2.5f));
    	
       	brotService.save(new BrotBestand("327", "Dinkel Gemüse Tartes", true, 0, 2.5f));
       	brotService.save(new BrotBestand("328", "Quarkbällchen ab 3 St", true, 0, 0.8f));
       	brotService.save(new BrotBestand("341", "Apfel-Blech-Kuchen", true, 0, 2.1f));
       	brotService.save(new BrotBestand("342", "Schoko-Kirsch-Kuchen", true, 0, 2.1f));
    	brotService.save(new BrotBestand("343", "Schwarze Johannisbeer-Kuchen", true, 0, 2.1f));
    	
       	brotService.save(new BrotBestand("351", "Rhabarberkuchen", true, 0, 2.1f));
       	brotService.save(new BrotBestand("352", "Zwetschgenkuchen", false, 0, 2.1f));
       	brotService.save(new BrotBestand("353", "Mohnkuchen", true, 0, 2.2f));
       	brotService.save(new BrotBestand("401", "18 Amaranth-Taler vegan", true, 0, 26.4f));
       	brotService.save(new BrotBestand("402", "Linzer-Torte", true, 0, 10.45f));
    	brotService.save(new BrotBestand("405", "16 Dinkel-Mandel-Zungen", true, 0, 28.4f));
    	
       	brotService.save(new BrotBestand("406", "16 Dinkel-Nougat-Ring", true, 0, 28.4f));
       	brotService.save(new BrotBestand("407", "18 Schoko-Peanuts Vollm.", true, 0, 30.3f));
       	brotService.save(new BrotBestand("422", "12 Kernbeißer", true, 0, 21.2f));

       	
       	//Lagerware
       	produktService.save(new Produkt("Backhefe 7g","Biovegan Meister Backhefe", Backmittel, new Lagerbestand(g, 0d, 0d), 0.65f));
       	produktService.save(new Produkt("Backhefe 9g","Rapunzel", Backmittel, new Lagerbestand(g, 0d, 0d), 0.70f));
       	produktService.save(new Produkt("Backpulver 3x17g","Biovegan Meister Backhefe", Backmittel, new Lagerbestand(g, 0d, 0d), 0.90f));
       	produktService.save(new Produkt("Vanillezucker 5x8g","Biovegan Vanillezucker", Backmittel, new Lagerbestand(g, 0d, 0d), 3.45f));
       	produktService.save(new Produkt("Kakao 125g","Naturata Kakao schwach entölt", Backmittel, new Lagerbestand(g, 0d, 0d), 2.05f));
       	produktService.save(new Produkt("Puderzucker 200g","Naturata Puderzucker aus Rohrohrzucker", Backmittel, new Lagerbestand(g, 0d, 0d), 1.8f));
       	produktService.save(new Produkt("Speisestärke 250g","Bauk/Spielberger Speisestärke (Mais) sehr fein", Backmittel, new Lagerbestand(g, 0d, 0d), 1.6f));
       	
       	produktService.save(new Produkt("Rot/weiß-weinessig 0,5l","Byodo", Essigoel, new Lagerbestand(l, 0d, 0d), 2.60f));
       	produktService.save(new Produkt("Apfelessig 0,5l","Byodo", Essigoel, new Lagerbestand(l, 0d, 0d), 3.00f));
       	produktService.save(new Produkt("Bratöl (Sonnenblumenkerne) 0,75l","Byodo", Essigoel, new Lagerbestand(l, 0d, 0d), 6.00f));
       	produktService.save(new Produkt("Bratöl (Sonnenblumenkerne) 1l","Rapunzel Brat und Backöl ", Essigoel, new Lagerbestand(l, 0d, 0d), 6.75f));
       	produktService.save(new Produkt("Bratöl (Raps) 0,75l","Byodo", Essigoel, new Lagerbestand(l, 0d, 0d), 4.60f));
       	
       	produktService.save(new Produkt("Basmati Reis 1kg","Rapunzel Himalaya Basmati Reis", reis, new Lagerbestand(kg, 0d, 0d), 5.00f));
       	produktService.save(new Produkt("Milchreis 500g","Burgermühle", reis, new Lagerbestand(g, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Milchreis 1kg","Burgermühle", reis, new Lagerbestand(kg, 0d, 0d), 5.20f));
       	produktService.save(new Produkt("Parboiled Reis 1kg weiß","Spielberger/Burger", reis, new Lagerbestand(kg, 0d, 0d), 5.40f));
       	produktService.save(new Produkt("Camarque Reis 500g rot","Rapunzel", reis, new Lagerbestand(g, 0d, 0d), 4.50f));
       	
       	produktService.save(new Produkt("Grünkern 160g","Bauckhof demeter", Bratlinge, new Lagerbestand(g, 0d, 0d), 1.70f));
       	produktService.save(new Produkt("Falafel 160g","Bauckhof glutenfrei", Bratlinge, new Lagerbestand(g, 0d, 0d), 1.80f));
       	
       	
       	produktService.save(new Produkt("Bulgur 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.20f));
       	produktService.save(new Produkt("Cous Cous 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.70f));
       	produktService.save(new Produkt("Minutenpolenta 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Haferflocken Zartblatt 1kg","Spielgerber", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 3.40f));
       	produktService.save(new Produkt("Speisehirse 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.20f));
       	produktService.save(new Produkt("Speisehirse 1kg","Rapunzel", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 5.40f));
       	produktService.save(new Produkt("Quinoa weiß 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 4.50f));
       	produktService.save(new Produkt("Leinsaat 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 3.15f));
       	produktService.save(new Produkt("Sonnenblumenkerne 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.05f));
       	produktService.save(new Produkt("Sesam 250g","Rapunzel ungeschält", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.25f));
       	produktService.save(new Produkt("Dinkelgrieß 500g","Spielberger demeter", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Dinkelmehl 630 1kg","Burgermühle", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Weizenmehl 550 1kg","Spielberger", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 1.50f));
       	produktService.save(new Produkt("Weizen-Spätzle-Mehl 1kg","Spielberger", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 2.35f));
       	produktService.save(new Produkt("Dinkelmehl 1050","", GriesGetreide, new Lagerbestand(g, 0d, 0d), 2.60f));
       	produktService.save(new Produkt("Weizenmehl 1050 1kg","Burgermühle", GriesGetreide, new Lagerbestand(kg, 0d, 0d), 1.50f));
       	produktService.save(new Produkt("Kürbiskerne 500g","Rapunzel", GriesGetreide, new Lagerbestand(g, 0d, 0d), 9.90f));
       	
       	produktService.save(new Produkt("Vollmilch / ganze Mandel","Rapunzel", Sueskram, new Lagerbestand(p, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Cristallino Nougat","Rapunzel", Sueskram, new Lagerbestand(p, 0d, 0d), 2.30f));
       	produktService.save(new Produkt("Krachnuss Schokolade/Nirvana","Rapunzel", Sueskram, new Lagerbestand(p, 0d, 0d), 2.30f));
       	produktService.save(new Produkt("Capuccino","Vivani", Sueskram, new Lagerbestand(p, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Nougat / Praline","Vivani", Sueskram, new Lagerbestand(p, 0d, 0d), 2.30f));
       	produktService.save(new Produkt("Vollmilch","Vivani", Sueskram, new Lagerbestand(p, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Gummibärchen","", Sueskram, new Lagerbestand(p, 0d, 0d), 1.80f));
       	produktService.save(new Produkt("Kartoffelchips mit Salz","Plural", Sueskram, new Lagerbestand(p, 0d, 0d), 1.9f));
       	produktService.save(new Produkt("Geröstete Erdnüsse 150g","Morgenland", Sueskram, new Lagerbestand(p, 0d, 0d), 2.40f));
       	produktService.save(new Produkt("Doppelkekse Schoko/Vanille","Plural", Sueskram, new Lagerbestand(p, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Doppelkekse Schoko Dinkel","Plural", Sueskram, new Lagerbestand(p, 0d, 0d), 2.30f));
       	
       	produktService.save(new Produkt("Linsen braun 500g","Rapunzel", Huelsenfruechte, new Lagerbestand(g, 0d, 0d), 3.6f));
       	produktService.save(new Produkt("Linsen rot 500g","Rapunzel", Huelsenfruechte, new Lagerbestand(g, 0d, 0d), 3.5f));
       	produktService.save(new Produkt("Linsen beluga 500g","Rapunzel", Huelsenfruechte, new Lagerbestand(g, 0d, 0d), 4.0f));
       	produktService.save(new Produkt("Kichererbsen trocken 500g","Rapunzel", Huelsenfruechte, new Lagerbestand(g, 0d, 0d), 3.20f));
       	
       	produktService.save(new Produkt("Earl Grey","", Tee, new Lagerbestand(p, 0d, 0d), 3.4f));
       	produktService.save(new Produkt("Sandorn-Cranbery / Granatapfel-Orange","", Tee, new Lagerbestand(p, 0d, 0d), 2.8f));
       	produktService.save(new Produkt("Sweet Chai","", Tee, new Lagerbestand(p, 0d, 0d), 3.3f));
       	produktService.save(new Produkt("Lebensbaum Rooibusch","", Tee, new Lagerbestand(p, 0d, 0d), 3.30f));
       	
       	produktService.save(new Produkt("Thunfisch","", fisch, new Lagerbestand(k, 0d, 0d), 2.7f));
       	produktService.save(new Produkt("Sardinen","", fisch, new Lagerbestand(k, 0d, 0d), 0f));
       	
       	produktService.save(new Produkt("Basis-Müsli 750g","Rapunzel", muesli, new Lagerbestand(g, 0d, 0d), 2.5f));
       	produktService.save(new Produkt("Mond und Sterne 1kg","Rapunzel", muesli, new Lagerbestand(g, 0d, 0d), 8.05f));
       	produktService.save(new Produkt("Allos Hildegard Dinkel","Allos", muesli, new Lagerbestand(g, 0d, 0d), 4.10f));
       	produktService.save(new Produkt("Rapunzel Früchte 750g","Rapunzel", muesli, new Lagerbestand(g, 0d, 0d), 4.95f));
       	
       	produktService.save(new Produkt("Vollrohrzucker 1kg","Rapunzel Rapadura", suessmittel, new Lagerbestand(kg, 0d, 0d),7.05f));
       	produktService.save(new Produkt("Rohrzucker 1kg","Rapunzel Cristallino", suessmittel, new Lagerbestand(kg, 0d, 0d), 4.10f));
       	produktService.save(new Produkt("Roh-Rohrzucker 1kg","Burgermühle", suessmittel, new Lagerbestand(kg, 0d, 0d), 2.60f));
       	
      	produktService.save(new Produkt("Vanille/Schoko","Rapunzel", pudding, new Lagerbestand(kg, 0d, 0d), 0.90f));
       	
       	produktService.save(new Produkt("Shoyu 500ml","Lima Shoyu mild", wuerze, new Lagerbestand(ml, 0d, 0d), 6.10f));
       	produktService.save(new Produkt("Meersalz fein 1kg","Byodo ohne Riesenhilfe", wuerze, new Lagerbestand(kg, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Kräutersalz Nachfüllpack","Byodo", wuerze, new Lagerbestand(p, 0d, 0d), 4.10f));
       	produktService.save(new Produkt("klare Suppe Nachfüllpack","Rapunzel", wuerze, new Lagerbestand(p, 0d, 0d), 5.30f));
       	produktService.save(new Produkt("Grill / Fondue Senf 200ml","Byodo", wuerze, new Lagerbestand(ml, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Ganzkorn Senf 160ml","Zwergenwiese", wuerze, new Lagerbestand(ml, 0d, 0d), 2.00f));
       	produktService.save(new Produkt("Mittelscharfer Senf 200ml","Byodo", wuerze, new Lagerbestand(ml, 0d, 0d), 2.00f));
       	produktService.save(new Produkt("Kräuter der Provence 30g","Lebensbaum", wuerze, new Lagerbestand(g, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Zimt","Lebensbaumr", wuerze, new Lagerbestand(p, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Paprika edelsüß","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 2.30f));
       	produktService.save(new Produkt("Koriander gemahlen","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 2.00f));
       	produktService.save(new Produkt("Curry mild","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 3.00f));
       	produktService.save(new Produkt("Oregano /Tymian","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 1.70f));
       	produktService.save(new Produkt("Basilikum gerebelt","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Pfeffer","Lebensbaum", wuerze, new Lagerbestand(p, 0d, 0d), 2.60f));
       	
       	produktService.save(new Produkt("Ratatouille 350g","Rapunzel", Pastasosen, new Lagerbestand(g, 0d, 0d), 3.70f));
       	produktService.save(new Produkt("Toskana 550g","Rapunzel", Pastasosen, new Lagerbestand(g, 0d, 0d), 3.00f));
       	produktService.save(new Produkt("Familia 550g","Rapunzel", Pastasosen, new Lagerbestand(g, 0d, 0d), 2.80f));
       	produktService.save(new Produkt("Ricotta  350g","Zwergenwiese", Pastasosen, new Lagerbestand(g, 0d, 0d), 2.70f));
       	produktService.save(new Produkt("Tomatensauce Olivia 370g","Rapunzel Tomatensauce", Pastasosen, new Lagerbestand(g, 0d, 0d), 3.50f));
       	produktService.save(new Produkt("Pesto grün","Byodo", Pastasosen, new Lagerbestand(p, 0d, 0d), 3.60f));
       	produktService.save(new Produkt("Pesto rot","Rapunzel", Pastasosen, new Lagerbestand(p, 0d, 0d), 3.70f));
       	
       	produktService.save(new Produkt("Tomaten-Ketchup","Byodo", Tomaten, new Lagerbestand(p, 0d, 0d), 3.70f));
       	produktService.save(new Produkt("Passata 680g","Rapunzel", Tomaten, new Lagerbestand(g, 0d, 0d), 2.50f));
       	produktService.save(new Produkt("Pizzatomaten 330g","Rapunzel", Tomaten, new Lagerbestand(g, 0d, 0d), 2.30f));
       	
       	produktService.save(new Produkt("Spaghetti 500g","Rapunzel", Nudeln, new Lagerbestand(g, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Spirelli 2kg","Rapunzel", Nudeln, new Lagerbestand(kg, 0d, 0d), 6.20f));
       	produktService.save(new Produkt("Penne 1kg","Rapunzel", Nudeln, new Lagerbestand(kg, 0d, 0d), 3.40f));
       	produktService.save(new Produkt("Farfalle 500g","Rapunzel", Nudeln, new Lagerbestand(g, 0d, 0d), 2.10f));
       	produktService.save(new Produkt("Lasagne-Platten 250g","Rapunzel", Nudeln, new Lagerbestand(g, 0d, 0d), 2.30f));
       	produktService.save(new Produkt("Tagliatelle Semola 500g","Rapunzel", Nudeln, new Lagerbestand(g, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Penne 500g","Rapunzel", Nudeln, new Lagerbestand(g, 0d, 0d), 1.90f));
       	
       	produktService.save(new Produkt("Samba Jumbo","Rapunzel", brotaufstrich, new Lagerbestand(p, 0d, 0d), 12.00f));
       	produktService.save(new Produkt("Samba Dark / Kokos 250g","Rapunzel", brotaufstrich, new Lagerbestand(g, 0d, 0d), 4.50f));
       	produktService.save(new Produkt("Erdnussmus fein / crun.","Rapunzel", brotaufstrich, new Lagerbestand(p, 0d, 0d), 5.90f));
       	produktService.save(new Produkt("Arrabitom, Mango-Curry, Rucola-Senf, Meditom, Sendi","Zwergenwiese", brotaufstrich, new Lagerbestand(p, 0d, 0d), 2.60f));
       	produktService.save(new Produkt("Fenchel Toskana","Fenchel Toskana", brotaufstrich, new Lagerbestand(p, 0d, 0d), 3.00f));
       	produktService.save(new Produkt("Gelbe Linse Grillgemüse, Hofgemüse Paprika Trio","Allos", brotaufstrich, new Lagerbestand(p, 0d, 0d), 2.80f));
       	
       	produktService.save(new Produkt("Kokosmilch","Morgenland", milch, new Lagerbestand(p, 0d, 0d), 2.40f));
       	produktService.save(new Produkt("Provamel","Provamel", milch, new Lagerbestand(p, 0d, 0d), 1.90f));
       	produktService.save(new Produkt("Haferdrink natural 1l","Natumi", milch, new Lagerbestand(l, 0d, 0d), 2.00f));
       	
       	produktService.save(new Produkt("Sauerkirsche 360g","Morgenland", gemuese, new Lagerbestand(p, 0d, 0d), 2.75f));
       	produktService.save(new Produkt("Apfelmark groß 700g","Bauckhof", gemuese, new Lagerbestand(g, 0d, 0d), 2.70f));
       	produktService.save(new Produkt("rote KidneyBohnen 350g","De Rit", gemuese, new Lagerbestand(p, 0d, 0d), 1.40f));
       	produktService.save(new Produkt("rote KidneyBohnen 400g","Rapunzel", gemuese, new Lagerbestand(p, 0d, 0d), 1.65f));
       	produktService.save(new Produkt("Mais 340g","Rapunzel", gemuese, new Lagerbestand(p, 0d, 0d), 1.80f));
       	produktService.save(new Produkt("Mais 160g","Rapunzel", gemuese, new Lagerbestand(p, 0d, 0d), 1.35f));
       	produktService.save(new Produkt("Kichererbsen","Rapunzel", gemuese, new Lagerbestand(p, 0d, 0d), 1.20f));
       	produktService.save(new Produkt("Kichererbsen Glas 400g","", gemuese, new Lagerbestand(p, 0d, 0d), 1.25f));
       	produktService.save(new Produkt("Gewürzgurken 670g","Marschland", gemuese, new Lagerbestand(p, 0d, 0d), 3.90f));
       	
    	}
    }
}
