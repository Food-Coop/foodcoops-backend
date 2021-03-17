package de.dhbw.foodcoop.warehouse.domain.values;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Lagerbestand {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "einheit_id", referencedColumnName = "id")
    private final Einheit einheit;
    @Column
    private final Integer istLagerbestand;
    @Column
    private final Integer sollLagerbestand;

    public Lagerbestand(Einheit einheit, Integer istLagerbestand, Integer sollLagerbestand) {
        Validate.notNull(einheit);
        Validate.isTrue(istLagerbestand >= 0);
        Validate.isTrue(sollLagerbestand >= 0);
        Validate.isTrue(istLagerbestand <= sollLagerbestand);
        this.einheit = einheit;
        this.istLagerbestand = istLagerbestand;
        this.sollLagerbestand = sollLagerbestand;
    }

    public Lagerbestand() {
        this(new Einheit(), 0, 0);
    }

    public Einheit getEinheit() {
        return einheit;
    }

    public Integer getIstLagerbestand() {
        return istLagerbestand;
    }

    public Integer getSollLagerbestand() {
        return sollLagerbestand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lagerbestand that = (Lagerbestand) o;
        return einheit.equals(that.einheit) && istLagerbestand.equals(that.istLagerbestand) && sollLagerbestand.equals(that.sollLagerbestand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(einheit, istLagerbestand, sollLagerbestand);
    }

    @Override
    public String toString() {
        return "Lagerbestand{" +
                "einheit=" + einheit +
                ", istLagerbestand=" + istLagerbestand +
                ", sollLagerbestand=" + sollLagerbestand +
                '}';
    }
}
