package com.example.se2_projekt_app.enums;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ElementTest {

    @Test
    void testGetSymbolAndTranslate_ValidSymbols() {
        assertEquals(Element.ROBOT, Element.getSymbolAndTranslate("ROBOTER"));
        assertEquals(Element.WATER, Element.getSymbolAndTranslate("WASSER"));
        assertEquals(Element.PLANT, Element.getSymbolAndTranslate("PFLANZE"));
        assertEquals(Element.ENERGY, Element.getSymbolAndTranslate("ENERGIE"));
        assertEquals(Element.SPACESUIT, Element.getSymbolAndTranslate("RAUMANZUG"));
        assertEquals(Element.PLANNING, Element.getSymbolAndTranslate("PLANNUNG"));
        assertEquals(Element.WILDCARD, Element.getSymbolAndTranslate("ANYTHING"));
    }

    @Test
    void testGetSymbolAndTranslate_InvalidSymbol() {
        assertThrows(IllegalArgumentException.class, () ->
            Element.getSymbolAndTranslate("INVALID_SYMBOL")
        );
    }



    @Test
    void testGetSymbolAndTranslate_EmptySymbol() {
        assertThrows(IllegalArgumentException.class, () ->
            Element.getSymbolAndTranslate("")
        );
    }

    @Test
    void testGetSymbolAndTranslate_MixedCaseSymbol() {
        // Test with a symbol in mixed case
        assertThrows(IllegalArgumentException.class, () ->
            Element.getSymbolAndTranslate("Robot")
        );
    }
}
