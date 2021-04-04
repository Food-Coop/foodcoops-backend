package de.dhbw.foodcoop.warehouse.application.bestellungsliste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class BestellungslistenService {
    private final KopfService kopfService;
    private final BestellungMapper bestellungMapper;

    public BestellungslistenService(KopfService kopfService, BestellungMapper bestellungMapper) {
        this.kopfService = kopfService;
        this.bestellungMapper = bestellungMapper;
    }
}
