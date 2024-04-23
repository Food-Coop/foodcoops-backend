package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "lagerprodukt")
public class Produkt extends BestandEntity {

	@Column
	private String produktName;
	
    @Embedded
    private Lagerbestand lagerbestand;
    @ManyToOne
    @JoinColumn(name = "kategorie_id")
    private Kategorie kategorie;
    


    public Produkt(String id, String name, String produktname, Kategorie kategorie, Lagerbestand lagerbestand, float preis) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        Validate.notNull(lagerbestand);
        this.id = id;
        this.name = name;
        this.kategorie = kategorie;
        this.lagerbestand = lagerbestand;
    }

    public Produkt(String name, String produktname, Kategorie kategorie, Lagerbestand lagerbestand, float preis) {
        this(UUID.randomUUID().toString(), name,produktname,  kategorie, lagerbestand, preis);
    }

    protected Produkt() {
    }


    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    
    public String getProduktName() {
		return produktName;
	}

	public void setProduktName(String produktName) {
		this.produktName = produktName;
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
