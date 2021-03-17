package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "einheit")
public final class Einheit {
    @Id
    private final String id;
    @Column
    private final String name;

    public Einheit(String id, String name) {
        Validate.notBlank(id);
        this.id = id;
        Validate.notBlank(name);
        this.name = name;
    }

    public Einheit(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Einheit() {
        this("undefined");
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
        Einheit einheit = (Einheit) o;
        return name.equals(einheit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Einheit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
