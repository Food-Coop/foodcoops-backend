package de.dhbw.foodcoop.warehouse.domain.entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;

@Entity
@Table(name = "lagerprodukt")
public class Produkt extends BestandEntity {

	@Column
	private String produktBezeichnung;
	
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lagerbestand_id")
    private Lagerbestand lagerbestand;
    
    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
    

    public Produkt(String id, String name, String produktBezeichnung, Kategorie kategorie, Lagerbestand lagerbestand, float preis) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        Validate.notNull(lagerbestand);
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.produktBezeichnung = produktBezeichnung;
        this.preis = preis;
        this.lagerbestand = lagerbestand;
    }

    public Produkt(String name, String produktBezeichnung, Kategorie kategorie, Lagerbestand lagerbestand, float preis) {
        this(UUID.randomUUID().toString(), name,produktBezeichnung,  kategorie, lagerbestand, preis);
    }

    protected Produkt() {
    }


    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    


	public String getProduktBezeichnung() {
		return produktBezeichnung;
	}

	public void setProduktBezeichnung(String produktBezeichnung) {
		this.produktBezeichnung = produktBezeichnung;
	}

	public void setLagerbestand(Lagerbestand lagerbestand) {
		this.lagerbestand = lagerbestand;
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
