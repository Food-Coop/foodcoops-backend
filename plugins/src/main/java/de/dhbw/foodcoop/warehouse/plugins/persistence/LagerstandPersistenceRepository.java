package de.dhbw.foodcoop.warehouse.plugins.persistence;

import de.dhbw.foodcoop.warehouse.domain.entities.Lagerbestand;
import de.dhbw.foodcoop.warehouse.domain.repositories.LagerbestandRepository;
import de.dhbw.foodcoop.warehouse.domain.values.Menge;
import de.dhbw.foodcoop.warehouse.domain.values.Produkt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LagerstandPersistenceRepository implements LagerbestandRepository {

    private final SpringDataLagerbestandRepository springDataLagerbestandRepository;

    @Autowired
    public LagerstandPersistenceRepository(SpringDataLagerbestandRepository springDataLagerbestandRepository) {
        this.springDataLagerbestandRepository = springDataLagerbestandRepository;
    }

    @Override
    public Lagerbestand istLagerbestandAktualisieren(Produkt produkt, Menge neuerIstLagerbestand) {
        return null;
    }

    @Override
    public Lagerbestand sollLagerbestandAktualisieren(Produkt produkt, Menge neuerSollLagerbestand) {
        return null;
    }

    //dummy method
    @Override
    public Lagerbestand saveLagerbestand(Lagerbestand lagerbestand) {
        return springDataLagerbestandRepository.save(lagerbestand);
    }

    @Override
    public List<Lagerbestand> abrufenAlleLagerbestand() {
        return springDataLagerbestandRepository.findAll();
    }
}
