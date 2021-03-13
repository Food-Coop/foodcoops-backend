package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lagerbestand")
public class LagerbestandRow {
    @Id
    private String id;
    @Column
    private Integer istLagerbestand;
    @Column
    private Integer sollLagerbestand;
    @Column
    private String einheit;

    public LagerbestandRow() {
    }

    public LagerbestandRow(String id, Integer istLagerbestand, Integer sollLagerbestand, String einheit) {
        this.id = id;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
        this.einheit = einheit;
    }

    public String getId() {
        return id;
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
