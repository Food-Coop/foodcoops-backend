package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Icon;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "kategorie")
public final class Kategorie {
    @Id
    private String id;
    @Column
    private String name;

    public Kategorie(String id, String name) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        this.id = id;
        this.name = name;
    }

    public Kategorie(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    protected Kategorie() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kategorie kategorie = (Kategorie) o;
        return id.equals(kategorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Kategorie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
