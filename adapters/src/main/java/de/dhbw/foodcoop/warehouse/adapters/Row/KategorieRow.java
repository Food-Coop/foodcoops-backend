package de.dhbw.foodcoop.warehouse.adapters.Row;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "kategorie")
public class KategorieRow {
    @Id
    private final String id;
    @Column
    private final String name;
    @Column
    private final String icon;
    @OneToMany(mappedBy = "kategorie")
    private List<ProduktRow> produktRows;

    public KategorieRow(String id, String name, String icon, List<ProduktRow> produktRows) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.produktRows = produktRows;
    }

    public KategorieRow(String name, String icon) {
        this(UUID.randomUUID().toString(), name, icon, null);
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

    public List<ProduktRow> getProduktRows() {
        return produktRows;
    }

    public void setProduktRows(List<ProduktRow> produktRows) {
        this.produktRows = produktRows;
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
