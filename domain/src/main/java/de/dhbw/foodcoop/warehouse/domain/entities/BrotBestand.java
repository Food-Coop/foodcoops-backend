package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "brotbestand")
public class BrotBestand {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private boolean verfuegbarkeit;
    @Column
    private long gewicht;
    @Column
    private float preis;

    public BrotBestand(String id, String name, boolean verfuegbarkeit, long gewicht, float preis) {
        // Validate.notBlank(id);
        // Validate.notBlank(name);
        // Validate.notNull(verfuegbarkeit);
        // Validate.notNull(gebindegroesse);
        // Validate.notNull(preis);
        this.id = id;
        this.name = name;
        this.verfuegbarkeit = verfuegbarkeit;
        this.gewicht = gewicht;
        this.preis = preis;
    }

    public BrotBestand(String name, boolean verfuegbarkeit, long gewicht, float preis) {
        this(UUID.randomUUID().toString(), name, verfuegbarkeit, gewicht, preis);
    }

    public BrotBestand() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getVerfuegbarkeit() {
        return verfuegbarkeit;
    }

    public long getGewicht() {
        return gewicht;
    }

    public float getPreis() {
        return preis;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVerfuegbarkeit(boolean verfuegbarkeit) {
        this.verfuegbarkeit = verfuegbarkeit;
    }

    public void setGewicht(long gewicht){
        this.gewicht = gewicht;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrotBestand brot = (BrotBestand) o;
        return id.equals(brot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BrotBestand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", verfuegbarkeit= '" + verfuegbarkeit + '\'' +
                ", gewicht=" + gewicht + '\'' +
                ", preis=" + preis +
                '}';
    }
}
