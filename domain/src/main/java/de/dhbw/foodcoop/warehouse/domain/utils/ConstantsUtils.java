package de.dhbw.foodcoop.warehouse.domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConstantsUtils {

	public final static String EINKAUF_PLACEHOLDER_BROT = "%brotEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_FRISCH = "%frischEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_LAGER = "%lagerEInkauf%";
	public final static String EINKAUF_PLACEHOLDER_DATE = "%currentDate%";
	
	public final static String PLACEHOLDER_PERSONID = "%personID%";
	public final static String PLACEHOLDER_GESAMT_KOSTEN = "%gesamtKosten%";
	
	public final static String EMAIL_TEXT_EINKAUF_UEBERSICHT =  "Hallo " + ConstantsUtils.EINKAUF_PLACEHOLDER_BROT+ ",\n"
      		+ "\n"
      		+ "Dein Einkauf bei der Foodcoop in der Karlsruher Nordstadt am "+ ConstantsUtils.EINKAUF_PLACEHOLDER_DATE+ " war erfolgreich!\n"
      				+ "Anbei befindet sich der Beleg für den Einkauf als Pdf.\n"
      				+ "Hier nochmal eine Auflistung deines Einkaufs\n"
      				+ "\n"
      				+ "Frischwaren:\n"
      				+ ConstantsUtils.EINKAUF_PLACEHOLDER_FRISCH + "\n\n\n"
      						+ "Brot: \n"
      						+ ConstantsUtils.EINKAUF_PLACEHOLDER_BROT + "\n\n\n"
      								+ "Lagerware: \n"
      								+ ConstantsUtils.EINKAUF_PLACEHOLDER_LAGER + "\n\n\n"
      										+ "Gesamtpreis: " + ConstantsUtils.PLACEHOLDER_GESAMT_KOSTEN  + "€ \n"
      												+ "\n"
      												+ "Weitere Details können aus der PDF entnommen werden! \n\n\n"
      												+ "Viele Grüße\n"
      												+ "Eure Foodcoop Mika";
	
	public final static String CONFIGURATION_ID = "ConfigID";
}
