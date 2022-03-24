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
public class RepresentationToLagerbestandMapperTest {
    @Mock private RepresentationToEinheitMapper toEinheitMapper;
    @InjectMocks private RepresentationToLagerbestandMapper toBeTested;

    @Test
    @DisplayName("Representation to Lagerbestand mapping with success")
    public void applySuccessfully() {
        String testEinheit = "test einheit";
        EinheitRepresentation einheitRepresentation = new EinheitRepresentation(
                TestUtils.EINHEIT_TEST_ID
                , testEinheit
        );
        LagerbestandRepresentation lagerbestandRepresentation = new LagerbestandRepresentation(
                einheitRepresentation
                ,1.0
                ,19.0
        );

        when(toEinheitMapper.applyById(einheitRepresentation.getId()))
                .thenReturn(new Einheit(einheitRepresentation.getId(), einheitRepresentation.getName()));
        Lagerbestand mapped = toBeTested.apply(lagerbestandRepresentation);

        Assertions.assertNotNull(mapped);
        Assertions.assertNotNull(mapped.getEinheit());
        Assertions.assertEquals(TestUtils.EINHEIT_TEST_ID, mapped.getEinheit().getId());
        Assertions.assertEquals(testEinheit, mapped.getEinheit().getName());
        Assertions.assertEquals(lagerbestandRepresentation.getIstLagerbestand(), mapped.getIstLagerbestand());
        Assertions.assertEquals(lagerbestandRepresentation.getSollLagerbestand(), mapped.getSollLagerbestand());
    }
}