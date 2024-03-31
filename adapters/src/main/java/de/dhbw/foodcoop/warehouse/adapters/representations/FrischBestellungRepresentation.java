package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.sql.Timestamp;

public class FrischBestellungRepresentation extends BestellungRepresentation{

    private FrischBestandRepresentation frischbestand;


    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, double bestellmenge, Timestamp datum, boolean isDone) {
      super(id, person_id, datum, bestellmenge, isDone);
        this.frischbestand = frischbestand;
    }

//    public FrischBestellungRepresentation(String id, String person_id, FrischBestandRepresentation frischbestand, double bestellmenge) {
//        this.id = id;
//        this.person_id = person_id;
//        this.frischbestand = frischbestand;
//        this.bestellmenge = bestellmenge;
//    }



    public FrischBestandRepresentation getFrischbestand(){
        return frischbestand;
    }

    public void setFrischbestand(FrischBestandRepresentation frischbestand){
        this.frischbestand = frischbestand;
    }

	public void setId(String id) {
		super.id = id;
	}


}
