package de.dhbw.foodcoop.warehouse.domain.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "Person")
public class Person {
	
    @Id
    private String id;

    @OneToMany
    @JoinColumn(name = "offeneBestellungen_id")
    private List<BestellungEntity> bestellungen;
    
	
    public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person(String id,  List<BestellungEntity> bestellungen) {
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



	public List<BestellungEntity> getBestellungen() {
		return bestellungen;
	}

	public void setBestellungen(List<BestellungEntity> bestellungen) {
		this.bestellungen = bestellungen;
	}



    
    
}
