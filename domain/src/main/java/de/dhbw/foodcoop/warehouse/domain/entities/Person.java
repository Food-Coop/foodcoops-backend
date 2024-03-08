package de.dhbw.foodcoop.warehouse.domain.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.dhbw.foodcoop.warehouse.domain.values.EinkaufBestellungVergleich;

@Entity
@Table(name = "Person")
public class Person {
	
    @Id
    private String id;

    @OneToMany
    @JoinColumn(name = "offeneBestellungen_id")
    private List<EinkaufBestellungVergleich> bestellungen;
    
	
    public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(String id,  List<EinkaufBestellungVergleich> bestellungen) {
		super();
		this.id = id;
		this.bestellungen = bestellungen;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public List<EinkaufBestellungVergleich> getBestellungen() {
		return bestellungen;
	}

	public void setBestellungen(List<EinkaufBestellungVergleich> bestellungen) {
		this.bestellungen = bestellungen;
	}



    
    
}
