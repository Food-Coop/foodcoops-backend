package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.application.lager.EinheitService;
import de.dhbw.foodcoop.warehouse.domain.exceptions.EinheitNotFoundException;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RepresentationToEinheitMapperTest {
    @Mock private EinheitService einheitService;
    @InjectMocks private RepresentationToEinheitMapper toBeTested;

    @Test
    @DisplayName("Representation Mapped to Einheit")
    public void apply() {
        EinheitRepresentation representation = getEinheitRepresentation();

        Einheit einheit = toBeTested.apply(representation);

        Assertions.assertNotNull(einheit);
        Assertions.assertEquals(representation.getId(), einheit.getId());
        Assertions.assertEquals(representation.getName(), einheit.getName());
    }

    @Test
    @DisplayName("Representation mapped to Einheit from other mapper")
    public void applyByIdSuccessfully() {
        EinheitRepresentation representation = getEinheitRepresentation();

        when(einheitService.findById(representation.getId()))
                .thenReturn(Optional.of(new Einheit(representation.getId(), representation.getName())));
        Einheit einheit = toBeTested.applyById(representation.getId());

        Assertions.assertDoesNotThrow(() -> new EinheitNotFoundException(representation.getId()));
        Assertions.assertNotNull(einheit);
        Assertions.assertEquals(representation.getId(), einheit.getId());
        Assertions.assertEquals(representation.getName(), einheit.getName());
    }

    @Test
    @DisplayName("Representation not mapped to Einheit due to Exception")
    public void applyByIdThrowsException() {
        EinheitRepresentation representation = getEinheitRepresentation();

        when(einheitService.findById(representation.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(EinheitNotFoundException.class
                , () -> toBeTested.applyById(representation.getId()));
    }

    private EinheitRepresentation getEinheitRepresentation() {
        return new EinheitRepresentation(TestUtils.EINHEIT_TEST_ID, "test");
    }
}