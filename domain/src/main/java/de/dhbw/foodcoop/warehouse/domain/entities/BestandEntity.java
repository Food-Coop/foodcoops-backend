package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrischBestand.class, name = "frisch"),
    @JsonSubTypes.Type(value = BrotBestand.class, name ="brot"),
    @JsonSubTypes.Type(value = Produkt.class, name="lager")
    // Andere Subtypen hier
})
@Table(name = "bestand")
public abstract class BestandEntity {

    @Id
    protected String id;
    @Column
    protected String name;
    @Column
    protected boolean verfuegbarkeit;
    
    @Column
    protected float preis;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getVerfuegbarkeit() {
		return verfuegbarkeit;
	}

	public void setVerfuegbarkeit(boolean verfuegbarkeit) {
		this.verfuegbarkeit = verfuegbarkeit;
	}

	public float getPreis() {
		return preis;
	}

	public void setPreis(float preis) {
		this.preis = preis;
	}
    
    
}
