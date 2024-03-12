package de.dhbw.foodcoop.warehouse.application.diskrepanz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.dhbw.foodcoop.warehouse.domain.entities.DiscrepancyEntity;
import de.dhbw.foodcoop.warehouse.domain.repositories.DiscrepancyRepository;

@Service
public class DiscrepancyService {

    private final DiscrepancyRepository discrepancy;

    @Autowired
    public DiscrepancyService(DiscrepancyRepository discrepancy) {
        this.discrepancy = discrepancy;
    }
    
    public List<DiscrepancyEntity> findAll() {
    	return discrepancy.alle();
    }
    
    public void deleteById(String id) {
    	discrepancy.deleteById(id);
    }
    
    public DiscrepancyEntity findById(String id) {
    	return discrepancy.findeMitId(id).orElseThrow();
    }
    
    public DiscrepancyEntity save(DiscrepancyEntity entity) {
    	return discrepancy.speichern(entity);
    }
    
    /** Der Amount kann negativ sein, wenn z.B. zu viel Eingetragen wurde, und etwas davon genommen wurde
     * Ansonsten positiv, wenn zu wenig eingetragen wurde, und eine Person deshalb weniger nimmt.
     *  TODO: Eventuell muss hier der Einkauf automatisch aufrufen wenn bei einer Bestellung zu viel/zu wenig genommen wurde.
     * 
     * @param amount
     * @return
     */
    public DiscrepancyEntity updateDiscrepancy(String id, float amount) {
    	DiscrepancyEntity entity = findById(id);
    	entity.setAmount(entity.getAmount() + amount);
    	return save(entity);
    }
}
