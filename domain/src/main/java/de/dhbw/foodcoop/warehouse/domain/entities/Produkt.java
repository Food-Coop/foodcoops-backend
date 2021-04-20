package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "produkt")
public class Produkt {
    @Id
    private final String id;
    @Column
    private final String name;
    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
    @Embedded
    private final Lagerbestand lagerbestand;

    public Produkt(String id, String name, Kategorie kategorie, Lagerbestand lagerbestand) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        Validate.notNull(lagerbestand);
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public Produkt(String name, Kategorie kategorie, Lagerbestand lagerbestand) {
        this(UUID.randomUUID().toString(), name, kategorie, lagerbestand);
    }

    //for unittests
    public Produkt() {
        this(TestUtils.PRODUKT_TEST_ID
                , "undefined"
                , new Kategorie()
                , new Lagerbestand());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public Lagerbestand getLagerbestand() {
        return lagerbestand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produkt produkt = (Produkt) o;
        return id.equals(produkt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produkt{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kategorie= '" + kategorie.getName() + '\'' +
                ", lagerbestand=" + lagerbestand +
                '}';
    }
}
