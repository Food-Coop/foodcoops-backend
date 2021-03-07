package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.*;

@Entity
public class LagerbestandRow {
    @Id
    private final String id;
    @OneToOne
    @JoinColumn(name = "produkt_id")
    private final ProduktRow produktRow;
    @Column
    private final Integer istLagerbestand;
    @Column
    private final Integer sollLagerbestand;
    @Column
    private final String einheit;


    public LagerbestandRow(String id, ProduktRow produktRow, Integer istLagerbestand, Integer sollLagerbestand, String einheit) {
        this.id = id;
        this.produktRow = produktRow;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
        this.einheit = einheit;
    }

    public String getId() {
        return id;
    }

    public ProduktRow getProduktRow() {
        return produktRow;
    }

    public Integer getIstLagerbestand() {
        return istLagerbestand;
    }

    public Integer getSollLagerbestand() {
        return sollLagerbestand;
    }

    public String getEinheit() {
        return einheit;
    }
}
