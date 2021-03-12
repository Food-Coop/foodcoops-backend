package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;

import java.util.List;

public interface KategorieWahl {
    List<Kategorie> waehleKategorieAus();

    Kategorie fuegeNeueKategorieHinzu(Kategorie kategorie);

    Kategorie loescheUngenutzteKategorie(Kategorie kategorie);
}
