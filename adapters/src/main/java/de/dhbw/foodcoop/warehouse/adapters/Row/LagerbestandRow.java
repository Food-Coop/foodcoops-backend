package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lagerbestand")
public class LagerbestandRow {
    @Id
    private final String id;
    @Column
    private final Integer istLagerbestand;
    @Column
    private final Integer sollLagerbestand;
    @Column
    private final String einheit;


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
