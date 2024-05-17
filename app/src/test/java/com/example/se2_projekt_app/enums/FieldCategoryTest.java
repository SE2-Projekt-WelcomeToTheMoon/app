package com.example.se2_projekt_app.enums;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class FieldCategoryTest {

    // fixme extract in individual (parameterized) tests
    @Test
    void testGetSymbolAndTranslate_ValidSymbols() {
        assertEquals(FieldCategory.ROBOT, FieldCategory.getSymbolAndTranslate("ROBOTER"));
        assertEquals(FieldCategory.WATER, FieldCategory.getSymbolAndTranslate("WASSER"));
        assertEquals(FieldCategory.PLANT, FieldCategory.getSymbolAndTranslate("PFLANZE"));
        assertEquals(FieldCategory.ENERGY, FieldCategory.getSymbolAndTranslate("ENERGIE"));
        assertEquals(FieldCategory.SPACESUIT, FieldCategory.getSymbolAndTranslate("RAUMANZUG"));
        assertEquals(FieldCategory.PLANNING, FieldCategory.getSymbolAndTranslate("PLANNUNG"));
        assertEquals(FieldCategory.WILDCARD, FieldCategory.getSymbolAndTranslate("ANYTHING"));
    }

    @Test
    void testGetSymbolAndTranslate_InvalidSymbol() {
        assertThrows(IllegalArgumentException.class, () ->
                FieldCategory.getSymbolAndTranslate("INVALID_SYMBOL")
        );
    }



    @Test
    void testGetSymbolAndTranslate_EmptySymbol() {
        assertThrows(IllegalArgumentException.class, () ->
                FieldCategory.getSymbolAndTranslate("")
        );
    }

    @Test
    void testGetSymbolAndTranslate_MixedCaseSymbol() {
        // Test with a symbol in mixed case
        assertThrows(IllegalArgumentException.class, () ->
                FieldCategory.getSymbolAndTranslate("Robot")
        );
    }
}
