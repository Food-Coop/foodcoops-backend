package de.dhbw.foodcoop.warehouse.domain.values;

import de.dhbw.foodcoop.warehouse.domain.utils.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class IconTest {
    @Test
    @DisplayName("Validate a real Icon")
    public void validateSuccesfully() {
        new Icon(TestUtils.BASICICON);
        new Icon(TestUtils.TEIGWARENICON);
        new Icon(TestUtils.GEMUESEICON);
        Assertions.assertDoesNotThrow((ThrowingSupplier<IllegalArgumentException>) IllegalArgumentException::new);
    }

    @Test
    @DisplayName("Validate a false Icon")
    public void validateFail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Icon("\\r" + TestUtils.BASICICON));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Icon(TestUtils.TEIGWARENICON + " ;"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Icon("\\s" + TestUtils.GEMUESEICON));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Icon(TestUtils.TEIGWARENICON + " \\n"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Icon("; " + TestUtils.BASICICON));
    }

}