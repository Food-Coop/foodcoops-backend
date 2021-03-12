package de.dhbw.foodcoop.warehouse.application.LagerService;

import de.dhbw.foodcoop.warehouse.domain.values.Einheit;

import java.util.List;

public interface EinheitWahl {
    List<Einheit> waehleEinheitAus();

    Einheit fuegeNeueEinheitHinzu(Einheit einheit);

    Einheit loescheUngenutzteEinheit(Einheit einheit);
}
