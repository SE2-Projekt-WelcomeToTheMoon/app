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
        cardController.extractCardsFromServerString(serverString);
        CardCombination[] combinations=cardController.getCurrentCombination();
        assertEquals(Element.ROBOT, combinations[0].getCurrentSymbol());
        assertEquals(FieldValue.TWO, combinations[0].getCurrentNumber());
        assertEquals(Element.WATER, combinations[0].getNextSymbol());

    }


    @Test
    void testGetCurrentNumberFromInt() {
        assertEquals(FieldValue.ONE, cardController.getCurrentNumberFromInt(1));
        assertEquals(FieldValue.TWO, cardController.getCurrentNumberFromInt(2));
        assertEquals(FieldValue.THREE, cardController.getCurrentNumberFromInt(3));

    }
    @Test
    void testExtractCardsFromServerString_EmptyString() {
        String serverString = "";
        assertThrows(IllegalArgumentException.class, () ->
            cardController.extractCardsFromServerString(serverString)
        );
    }

    @Test
    void testExtractCardsFromServerString_InvalidNumberOfCombinations() {
        String serverString = "1-ROBOTER-2-WASSER;2-PLANT-3-ENERGIE";
        assertThrows(IllegalArgumentException.class, () ->
            cardController.extractCardsFromServerString(serverString)
        );
    }



    @Test
    void testGetCurrentNumberFromInt_InvalidValue() {
        assertThrows(IllegalArgumentException.class, () ->
            cardController.getCurrentNumberFromInt(100)
        );
    }
}
