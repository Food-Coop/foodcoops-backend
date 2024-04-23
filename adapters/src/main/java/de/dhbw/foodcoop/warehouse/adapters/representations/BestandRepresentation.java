package de.dhbw.foodcoop.warehouse.adapters.representations;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.dhbw.foodcoop.warehouse.domain.entities.BrotBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.FrischBestand;
import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FrischBestandRepresentation.class, name = "frisch"),
    @JsonSubTypes.Type(value = BrotBestandRepresentation.class, name ="brot"),
    @JsonSubTypes.Type(value = ProduktRepresentation.class, name="lager")
    // Andere Subtypen hier
})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BestandRepresentation {
		protected String id;
	    protected String name;
	    private boolean verfuegbarkeit;
	    private float preis;

	    public BestandRepresentation(String id, String name, boolean verfuegbarkeit, float preis) {
	        this.id = id;
	        this.name = name;
	        this.verfuegbarkeit = verfuegbarkeit;
	        this.preis = preis;
	    }

	    public BestandRepresentation() {
			// TODO Auto-generated constructor stub
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



	    public float getPreis() {
	        return preis;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }
}
