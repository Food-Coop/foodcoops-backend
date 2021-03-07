package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.entities.Produkt;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduktUIService {
    private final ProduktRepository produktRepository;

    @Autowired
    public ProduktUIService(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }


    public List<Produkt> getAllProdukts() {
        return produktRepository.alleProdukteAbrufen();
    }
}
