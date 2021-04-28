package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import de.dhbw.foodcoop.warehouse.domain.values.Briefkopf;

import java.io.File;
import java.io.IOException;


public interface ExterneBestellungslisteService {
    File createExterneListe() throws IOException;

    Briefkopf getBriefKopf();

    String getFileName();
}
