package de.dhbw.foodcoop.warehouse.domain.entities;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "kategorie")
public final class Kategorie {
    
	@Id
    private String id;
    @Column
    private String name;
    
    //Entscheidet, ob die Kategorie beim Gebindeproblem zusammengefügt werden darf.
    // Zum Beispiel Salat = true, wenn 3x Eichblatt und 2x Kopfsalat bestellt wurden und die einzelnen nicht für ein
    // Gebinde reichen dürfen diese zusammengelegt werden, also 5x Eichblatt und 0x Kopfsalat.
    // Bei False dürfen die Kategorien nicht gemixt werden. z.B. Kategorie Kräuter.
    // 1x Dill und 2x Petersilie, angenommen Gebindegröße bei beiden 3, dann darf nicht 0x Dill 3x Petersilie.
    
    @Column
    private boolean isMixable;

    public Kategorie(String id, String name, boolean isMixable) {
        Validate.notBlank(id);
        Validate.notBlank(name);
        this.id = id;
        this.name = name;
        this.isMixable = isMixable;
    }

    public Kategorie(String name, boolean isMixable) {
        this(UUID.randomUUID().toString(), name, isMixable);
    }

    protected Kategorie() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    


    public boolean isMixable() {
		return isMixable;
	}

	public void setMixable(boolean isMixable) {
		this.isMixable = isMixable;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
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
                ", isMixable='" + isMixable + '\'' +
                '}';
    }
}
