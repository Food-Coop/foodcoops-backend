package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "frischbestand")
public class FrischBestand {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private boolean verfuegbarkeit;
    @Column
    private String herkunftsland;
    @Column
    private int gebindegroesse;
    @ManyToOne
    @JoinColumn(name = "einheit_id")
    private Einheit einheit;
    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
    @Column
    private float preis;

    public FrischBestand(String id, String name, boolean verfuegbarkeit, String herkunftsland, int gebindegroesse, Einheit einheit, Kategorie kategorie, float preis) {
        // Validate.notBlank(id);
        // Validate.notBlank(name);
        // Validate.notNull(verfuegbarkeit);
        // Validate.notNull(gebindegroesse);
        // Validate.notNull(preis);

        this.id = id;
        this.name = name;
        this.verfuegbarkeit = verfuegbarkeit;
        this.herkunftsland = herkunftsland;
        this.gebindegroesse = gebindegroesse;
        this.einheit = einheit;
        this.kategorie = kategorie;
        this.preis = preis;
    }

    public FrischBestand(String name, boolean verfuegbarkeit, String herkunftsland, int gebindegroesse, Einheit einheit, Kategorie kategorie, float preis) {
        this(UUID.randomUUID().toString(), name, verfuegbarkeit, herkunftsland, gebindegroesse, einheit, kategorie, preis);
    }

    public FrischBestand() {

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

    public String getHerkunftsland() {
        return herkunftsland;
    }

    public int getGebindegroesse() {
        return gebindegroesse;
    }

    public Einheit getEinheit() {
        return einheit;
    }

    public Kategorie getKategorie(){
        return kategorie;
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

    public void setHerkunftsland(String herkunftsland) {
        this.herkunftsland = herkunftsland;
    }

    public void setGebindegroesse(int gebindegroesse) {
        this.gebindegroesse = gebindegroesse;
    }

    public void setEinheit(Einheit einheit) {
        this.einheit = einheit;
    }

    public void setKategorie(Kategorie kategorie){
        this.kategorie = kategorie;
    }

    public void setPreis(float preis) {
        this.preis = preis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrischBestand frischbestand = (FrischBestand) o;
        return id.equals(frischbestand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "FrischBestand{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", verfuegbarkeit= '" + verfuegbarkeit + '\'' +
                ", herkunftsland=" + herkunftsland + '\'' +
                ", gebindegroesse=" + gebindegroesse + '\'' +
                ", einheit=" + einheit.getName() + '\'' +
                ", kategorie=" + kategorie.getName() + '\'' +
                ", preis=" + preis +
                '}';
    }
}
