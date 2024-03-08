package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
