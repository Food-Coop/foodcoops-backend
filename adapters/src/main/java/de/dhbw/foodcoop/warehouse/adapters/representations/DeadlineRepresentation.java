package de.dhbw.foodcoop.warehouse.adapters.representations;

import java.security.Timestamp;

public class DeadlineRepresentation {
    private String id;
    private Timestamp datum;

    public DeadlineRepresentation(String id, Timestamp datum){
        this.id = id;
        this.datum = datum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }
    
}
