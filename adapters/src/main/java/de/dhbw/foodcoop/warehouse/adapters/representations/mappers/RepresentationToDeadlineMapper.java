package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.dhbw.foodcoop.warehouse.adapters.representations.DeadlineRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Deadline;

@Component
public class RepresentationToDeadlineMapper implements Function<DeadlineRepresentation, Deadline> {

    @Autowired
    public RepresentationToDeadlineMapper() {
    }

    @Override
    public Deadline apply(DeadlineRepresentation deadlineRepresentation) {
       return new Deadline(
                deadlineRepresentation.getId(),
                deadlineRepresentation.getDatum()
        );
    }

    public Deadline update(Deadline oldDeadline, DeadlineRepresentation newDeadline) {
        return new Deadline(
                oldDeadline.getId(),
                newDeadline.getDatum()
        );
    }
}

