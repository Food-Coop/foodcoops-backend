package de.dhbw.foodcoop.warehouse.domain.entities;

import de.dhbw.foodcoop.warehouse.domain.values.Icon;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public final class Kategorie {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    @Embedded
    private Icon icon;
    @OneToMany(mappedBy = "kategorie", cascade = CascadeType.MERGE)
    private List<Produkt> produkte;

    public Kategorie(String id, String name, Icon icon, List<Produkt> produkte) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        Validate.notNull(icon);
        Validate.notNull(produkte);
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produkte = produkte;
    }

    public Kategorie(String name, Icon icon, List<Produkt> produkte) {
        this(UUID.randomUUID().toString(), name, icon, produkte);
    }

    protected Kategorie() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Icon getIcon() {
        return icon;
    }

    public List<Produkt> getProdukte() {
        return produkte;
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
                ", icon='" + icon + '\'' +
                ", produkte=" + produkte +
                '}';
    }
}
