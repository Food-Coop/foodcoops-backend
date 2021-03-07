package de.dhbw.foodcoop.warehouse.application.LagerbestandAnsicht;

import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduktUIService {
    private final ProduktRepository produktRepository;

    @Autowired
    public ProduktUIService(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }


}
