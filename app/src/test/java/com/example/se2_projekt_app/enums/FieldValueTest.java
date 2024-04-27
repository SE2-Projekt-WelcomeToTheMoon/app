package com.example.se2_projekt_app.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FieldValueTest {

    @Test
    void testGetCurrentNumberFromInt_ValidValues() {
        assertEquals(FieldValue.ONE, FieldValue.getCurrentNumberFromInt(1));
        assertEquals(FieldValue.TWO, FieldValue.getCurrentNumberFromInt(2));
        assertEquals(FieldValue.FOUR, FieldValue.getCurrentNumberFromInt(4));
        assertEquals(FieldValue.FIFTEEN, FieldValue.getCurrentNumberFromInt(15));
    }

    @Test
    void testGetCurrentNumberFromInt_InvalidValues() {
        assertThrows(IllegalArgumentException.class, () ->
            FieldValue.getCurrentNumberFromInt(-1) // Negative value
        );

        assertThrows(IllegalArgumentException.class, () ->
            FieldValue.getCurrentNumberFromInt(16) // Value exceeding enum range
        );

        assertThrows(IllegalArgumentException.class, () ->
            FieldValue.getCurrentNumberFromInt(100) // Arbitrary invalid value
        );
    }
}
