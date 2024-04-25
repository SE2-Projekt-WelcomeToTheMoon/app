package com.example.se2_projekt_app.game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

public class CardControllerTest {
    CardController cardController;
    @BeforeEach
    void setup(){
        cardController=new CardController();
    }
    @Test
    void testExtractCardsFromServerString() {
        String serverString = "0-ROBOTER-2-WASSER;1-PFLANZE-3-ENERGIE;2-RAUMANZUG-4-PLANNUNG";
        CardCombination[] combinations = cardController.extractCardsFromServerString(serverString);
        assertEquals(Element.ROBOT, combinations[0].getCurrentSymbol());
        assertEquals(FieldValue.TWO, combinations[0].getCurrentNumber());
        assertEquals(Element.WATER, combinations[0].getNextSymbol());

    }

    @Test
    void testGetSymbolAndTranslate() {
        assertEquals(Element.ROBOT, cardController.getSymbolAndTranslate("ROBOTER"));
        assertEquals(Element.WATER, cardController.getSymbolAndTranslate("WASSER"));
        assertEquals(Element.PLANT, cardController.getSymbolAndTranslate("PFLANZE"));

    }

    @Test
    void testGetCurrentNumberFromInt() {
        assertEquals(FieldValue.ONE, cardController.getCurrentNumberFromInt(1));
        assertEquals(FieldValue.TWO, cardController.getCurrentNumberFromInt(2));
        assertEquals(FieldValue.THREE, cardController.getCurrentNumberFromInt(3));

    }
}
