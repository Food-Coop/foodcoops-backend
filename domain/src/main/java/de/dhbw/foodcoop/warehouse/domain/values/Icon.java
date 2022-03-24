package de.dhbw.foodcoop.warehouse.domain.values;

import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.utils.ValidateUtils;
import org.apache.commons.lang3.Validate;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

@Embeddable
public final class Icon {
    @Lob
    private final String icon;

    public Icon(String icon) {
        Validate.notNull(icon);
        Validate.matchesPattern(icon, ValidateUtils.ISICON);
        this.icon = icon;
    }

    protected Icon() {
        this(TestUtils.BASICICON);
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon1 = (Icon) o;
        return Objects.equals(icon, icon1.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon);
    }

    @Override
    public String toString() {
        return "Icon{" +
                "icon='" + icon + '\'' +
                '}';
    }
}
