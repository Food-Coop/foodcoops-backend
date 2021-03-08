package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class KategorieRow {
    @Id
    private final String id;
    @Column
    private final String name;
    @Column
    private final String icon;

    public KategorieRow(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public KategorieRow(String name, String icon) {
        this(UUID.randomUUID().toString(), name, icon);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KategorieRow that = (KategorieRow) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
