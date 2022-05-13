package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;

import java.io.IOException;


public interface ExterneBestellungslisteService {
    byte[] createExterneListe() throws IOException;

    byte[] createExterneListeGebinde() throws IOException;

    Briefkopf getBriefKopf();

    String getFileName();
}
