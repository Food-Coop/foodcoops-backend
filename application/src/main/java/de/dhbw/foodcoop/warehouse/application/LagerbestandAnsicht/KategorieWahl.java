package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.values.Kategorie;

import java.util.List;

public interface KategorieWahl {
    List<Kategorie> waehleKategorieAus();

    Kategorie fuegeNeueKategorieHinzu(Kategorie kategorie);

    Kategorie loescheUngenutzteKategorie(Kategorie kategorie);
}
