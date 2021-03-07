package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.LagerbestandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LagerbestandUIService {
    private final LagerbestandRepository lagerbestandRepository;

    @Autowired
    public LagerbestandUIService(LagerbestandRepository lagerbestandRepository) {
        this.lagerbestandRepository = lagerbestandRepository;
    }

    List<Lagerbestand> betrachteAlleLagerbestand() {
        return lagerbestandRepository.abrufenAlleLagerbestand();
    }
}
