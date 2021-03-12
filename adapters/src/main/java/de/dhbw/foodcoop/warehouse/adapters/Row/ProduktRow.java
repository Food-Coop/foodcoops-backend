package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.*;

@Entity
@Table(name = "produkt")
public class ProduktRow {
    @Id
    private String id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    private KategorieRow kategorie;
    @OneToOne
    @JoinColumn(name = "lagerbestand_id")
    private LagerbestandRow lagerbestand;

    public ProduktRow() {
    }

    public ProduktRow(String id, String name, KategorieRow kategorie, LagerbestandRow lagerbestand) {
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

    public KategorieRow getKategorie() {
        return kategorie;
    }

    public LagerbestandRow getLagerbestand() {
        return lagerbestand;
    }
}
