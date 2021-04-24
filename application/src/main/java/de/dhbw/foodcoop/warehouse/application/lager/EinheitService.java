package de.dhbw.foodcoop.warehouse.application.lager;

import de.dhbw.foodcoop.warehouse.domain.repositories.EinheitRepository;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitInUseException;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EinheitService {
    private final EinheitRepository einheitRepository;
    private final ProduktService produktService;

    @Autowired
    public EinheitService(EinheitRepository einheitRepository, ProduktService produktService) {
        this.einheitRepository = einheitRepository;
        this.produktService = produktService;
    }

    public List<Einheit> all() {
        return einheitRepository.alle();
    }

    public Optional<Einheit> findById(String id) {
        return einheitRepository.findeMitId(id);
    }

    public Einheit save(Einheit newEinheit) {
        List<Einheit> all = all();
        List<Einheit> einheits = all.stream()
                .filter(e -> e.getName().equals(newEinheit.getName()))
                .collect(Collectors.toList());
        if (!einheits.isEmpty()) {
            return einheits.get(0);
        }
        return einheitRepository.speichern(newEinheit);
    }

    public void deleteById(String id) throws EinheitInUseException {
        Optional<Einheit> toBeDeleted = einheitRepository.findeMitId(id);
        if (toBeDeleted.isEmpty()) {
            return;
        }
        if (produktService.all().stream()
                .anyMatch(produkt -> produkt.getLagerbestand().getEinheit().equals(toBeDeleted.get()))) {
            throw new EinheitInUseException(id);
        }
        einheitRepository.deleteById(id);
    }
}
