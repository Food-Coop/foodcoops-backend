package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

@Component
public class DeadlineToRepresentationMapper implements Function<Deadline, DeadlineRepresentation> {
   
    @Autowired
    public DeadlineToRepresentationMapper() {
    }

    @Override
    public DeadlineRepresentation apply(Deadline deadline) {
        return new DeadlineRepresentation(
                deadline.getId(),
                deadline.getDatum()
        );
    }
}
