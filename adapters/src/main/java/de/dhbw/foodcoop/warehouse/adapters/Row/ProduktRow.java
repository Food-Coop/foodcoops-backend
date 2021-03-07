package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.*;

@Entity
public class ProduktRow {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String kategorie;
    @OneToOne
    @JoinColumn(name = "lagerbestand_id")
    private LagerbestandRow lagerbestand;

    public ProduktRow() {
    }

    public ProduktRow(String id, String name, String kategorie, LagerbestandRow lagerbestand) {
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKategorie() {
        return kategorie;
    }

    public LagerbestandRow getLagerbestand() {
        return lagerbestand;
    }
}
