package de.dhbw.foodcoop.warehouse.domain.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConstantsUtils {

	public final static double THRESHOLD = 80;
	public final static String EINKAUF_PLACEHOLDER_BROT = "%brotEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_FRISCH = "%frischEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_ZUVIEL = "%zuVielEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_LAGER = "%lagerEinkauf%";
	public final static String EINKAUF_PLACEHOLDER_DATE = "%currentDate%";
	
	public final static String PLACEHOLDER_PERSONID = "%personID%";
	public final static String PLACEHOLDER_SHOPPER_PERSONID = "%personID%";
	
	public final static String PLACEHOLDER_BROT_KOSTEN = "%brotKosten%";
	public final static String PLACEHOLDER_FRISCH_KOSTEN = "%frischKosten%";
	public final static String PLACEHOLDER_ZUVIEL_KOSTEN = "%zuVielKosten%";
	public final static String PLACEHOLDER_LAGER_KOSTEN = "%lagerKosten%";
	public final static String PLACEHOLDER_LIEFER_KOSTEN = "%lieferKosten%";
	public final static String PLACEHOLDER_GESAMT_KOSTEN = "%gesamtKosten%";
	
	public final static String EMAIL_TEXT_EINKAUF_UEBERSICHT =  "Hallo " + ConstantsUtils.PLACEHOLDER_PERSONID + ",\n"
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
      								      	+ "Von der zu Viel Liste: \n"
      								        + ConstantsUtils.EINKAUF_PLACEHOLDER_ZUVIEL + "\n\n\n"
      												+ "Gesamtpreis: " + ConstantsUtils.PLACEHOLDER_GESAMT_KOSTEN  + "€ \n"
      														+ "\n"
		      												+ "Weitere Details können aus der PDF entnommen werden! \n\n\n"
		      												+ "Viele Grüße\n"
		      												+ "Eure Foodcoop Mika";
	
	public final static String EMAIL_TEXT_EINKAUFSMANAGEMENT = "Hallo " + ConstantsUtils.PLACEHOLDER_PERSONID + ",\n"
			+ "\n"
			+ "Mitglied " + ConstantsUtils.PLACEHOLDER_SHOPPER_PERSONID + " hat einen Einkauf am " + ConstantsUtils.EINKAUF_PLACEHOLDER_DATE + " durchgeführt.\n"
					+ "Folgender Betrag muss von " + ConstantsUtils.PLACEHOLDER_SHOPPER_PERSONID + " überwiesen werden:" + "\n"
					+ "  " + ConstantsUtils.PLACEHOLDER_BROT_KOSTEN + "€\n+ "
							+ ConstantsUtils.PLACEHOLDER_FRISCH_KOSTEN +"€\n+ "
									+ ConstantsUtils.PLACEHOLDER_LAGER_KOSTEN + "€\n+ "
											+ ConstantsUtils.PLACEHOLDER_ZUVIEL_KOSTEN + "€\n+ "
													+ ConstantsUtils.PLACEHOLDER_LIEFER_KOSTEN + "€\n\n= "
															+ ConstantsUtils.PLACEHOLDER_GESAMT_KOSTEN + "€\n\n"
																	+ "Viele Grüße \n"
																	+ "Deine Foodcoop MIKA";
	
	//Wenn Keycloak mal im Backend eingerichtet ist: Einmal die Woche an alle die Rolle "Lagermeistet" haben, ne Email senden mit dem Stand.
	public final static String EMAIL_TEXT_LAGERMEISTER = "Hallo " + ConstantsUtils.PLACEHOLDER_PERSONID + ",\n"
			+ "\n"
			+ "Im Anhang befindet sich der aktuelle Lagerbestand der Trockenware.\n\n"
			+ "Viele Grüße\n"
			+ "Deine Foodcoop MIKA"
			+ "";
	public final static String CONFIGURATION_ID = "ConfigID";
}
