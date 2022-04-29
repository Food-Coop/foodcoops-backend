package de.dhbw.foodcoop.warehouse.domain.entities;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "deadline")
public class Deadline {
    @Id
    private String id;
    @Column
    private Timestamp datum;

    public Deadline(String id, Timestamp datum){
        this.id = id;
        this.datum = datum;
    }

    public Deadline(Timestamp datum){
        this(UUID.randomUUID().toString(), datum);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public void setDatum(Timestamp datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datum == null) ? 0 : datum.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Deadline other = (Deadline) obj;
        if (datum == null) {
            if (other.datum != null)
                return false;
        } else if (!datum.equals(other.datum))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Deadline [datum=" + datum + ", id=" + id + "]";
    }

    
}
