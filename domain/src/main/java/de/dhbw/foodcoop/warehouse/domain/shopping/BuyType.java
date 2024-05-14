package de.dhbw.foodcoop.warehouse.domain.shopping;

public interface BuyType {

	public double getAmount();
	
	public void setAmount(double amount);
	
	//TODO: Umbauen das Buyable Objekte zurück gegeben werden, anstelle der implementierenden Klasse
	//Dafür untere Klassen auskommentieren und implementieren.
	//Achtung: Es muss geschaut werden, dass JPA entsprechend die Methode finden kann
	//Dann kann in zukunft mehr Code gespart werden. Siehe Studienarbeit "Umsetzung des Einkaufsprozesses"
	
	//public <T extends Buyable> void setBuyObject(T b);
	//public <T extends Buyable> T getBuyObject();
}
