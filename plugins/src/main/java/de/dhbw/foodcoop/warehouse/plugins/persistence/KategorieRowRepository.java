package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.adapters.Row.KategorieRow;
import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.KategorieRowToKategorieMapper;
import de.dhbw.foodcoop.warehouse.adapters.Row.Mapper.KategorieToKategorieRowMapper;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.repositories.KategorieRepository;
import de.dhbw.foodcoop.warehouse.domain.repositories.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class KategorieRowRepository implements KategorieRepository {
    private final ProduktRepository produktRepository;
    private final SpringDataKategorieRowRepository springDataKategorieRowRepository;
    private final KategorieRowToKategorieMapper kategorieRowToKategorieMapper;
    private final KategorieToKategorieRowMapper kategorieToKategorieRowMapper;

    @Autowired
    public KategorieRowRepository(ProduktRepository produktRepository,
                                  SpringDataKategorieRowRepository springDataKategorieRowRepository, KategorieRowToKategorieMapper kategorieRowToKategorieMapper,
                                  KategorieToKategorieRowMapper kategorieToKategorieRowMapper) {
        this.produktRepository = produktRepository;
        this.springDataKategorieRowRepository = springDataKategorieRowRepository;
        this.kategorieRowToKategorieMapper = kategorieRowToKategorieMapper;
        this.kategorieToKategorieRowMapper = kategorieToKategorieRowMapper;
    }

    @Override
    public List<Kategorie> alleKategorienAbrufen() {
        return springDataKategorieRowRepository.
                findAll().stream().
                map(kategorieRowToKategorieMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Kategorie speichern(Kategorie kategorie) {
        KategorieRow conversion = kategorieToKategorieRowMapper.apply(kategorie);
        kategorie.getProdukte().forEach(produktRepository::speichern);
        KategorieRow success = springDataKategorieRowRepository.save(conversion);
        return kategorieRowToKategorieMapper.apply(success);
    }
}
