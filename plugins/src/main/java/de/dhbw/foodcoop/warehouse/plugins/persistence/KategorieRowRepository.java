package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.KategorieRowToKategorieMapper;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class KategorieRowRepository implements KategorieRepository {
    private final SpringDataKategorieRowRepository springDataKategorieRowRepository;
    private final KategorieRowToKategorieMapper kategorieRowToKategorieMapper;

    @Autowired
    public KategorieRowRepository(SpringDataKategorieRowRepository springDataKategorieRowRepository,
                                  KategorieRowToKategorieMapper kategorieRowToKategorieMapper) {
        this.springDataKategorieRowRepository = springDataKategorieRowRepository;
        this.kategorieRowToKategorieMapper = kategorieRowToKategorieMapper;
    }

    @Override
    public List<Kategorie> alleKategorienAbrufen() {
        return springDataKategorieRowRepository.
                findAll().stream().
                map(kategorieRowToKategorieMapper)
                .collect(Collectors.toList());
    }
}
