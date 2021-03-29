package de.dhbw.foodcoop.warehouse.adapters.representations.mappers;

import de.dhbw.foodcoop.warehouse.adapters.representations.KategorieRepresentation;
import de.dhbw.foodcoop.warehouse.domain.entities.Kategorie;
import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KategorieToRepresentationMapperTest {
    @Mock
    ProduktToRepresentationMapper mockMapper;
    @InjectMocks
    KategorieToRepresentationMapper toBeTested;

    @Test
    @DisplayName("Kategorie To Representation Test")
    public void applySuccessfully() {
        Kategorie given = new Kategorie();
        KategorieRepresentation then = toBeTested.apply(given);
        Assertions.assertNotNull(then);
        Assertions.assertEquals(TestUtils.KATEGORIE_TEST_ID, then.getId());
        Assertions.assertEquals("undefined", then.getName());
        Assertions.assertEquals(TestUtils.BASICICON, then.getIcon());
        Assertions.assertNotNull(then.getProdukte());
        Assertions.assertTrue(then.getProdukte().isEmpty());
    }
}