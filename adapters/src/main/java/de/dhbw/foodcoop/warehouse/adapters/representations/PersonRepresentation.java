package de.dhbw.foodcoop.warehouse.adapters.representations;

public final class PersonRepresentation {
    private String id;
    private final String vorname;
    private final String nachname;
    
    public PersonRepresentation (String id, String vorname, String nachname){
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }
}
