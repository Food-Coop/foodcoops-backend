package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.EinheitRepresentation;
import de.dhbw.foodcoop.warehouse.adapters.representations.LagerbestandRepresentation;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import de.dhbw.foodcoop.warehouse.domain.values.Einheit;
import de.dhbw.foodcoop.warehouse.domain.values.Lagerbestand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LagerbestandToRepresentationMapperTest {
    @Mock
    private EinheitToRepresentationMapper einheitToRepresentationMapper;
    @InjectMocks
    private LagerbestandToRepresentationMapper toBeTested;

    @Test
    @DisplayName("Lagerbestand to Representation Mapper Simple Test")
    public void applySuccessfully() {
        String eimer = "Eimer";
        Einheit einheit = new Einheit(TestUtils.EINHEIT_TEST_ID, eimer);
        Lagerbestand lagerbestand = new Lagerbestand(einheit, 1.0, 3.5);

        when(einheitToRepresentationMapper.apply(einheit))
                .thenReturn(new EinheitRepresentation(
                        einheit.getId()
                        , einheit.getName()));
        LagerbestandRepresentation mapped = toBeTested.apply(lagerbestand);

        Assertions.assertNotNull(mapped);
        Assertions.assertNotNull(mapped.getEinheitRepresentation());
        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID, mapped.getEinheitRepresentation().getId());
        Assertions.assertEquals(eimer, mapped.getEinheitRepresentation().getName());
        Assertions.assertEquals(lagerbestand.getIstLagerbestand(), mapped.getIstLagerbestand());
        Assertions.assertEquals(lagerbestand.getSollLagerbestand(), mapped.getSollLagerbestand());
    }
}