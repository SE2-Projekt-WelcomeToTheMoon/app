package com.example.se2_projekt_app.game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.screens.GameScreen;
import com.example.se2_projekt_app.views.CardDrawView;

import java.lang.reflect.Field;

class CardControllerTest {
    private CardController cardController;
    @Mock
    private GameScreen mockGameScreen;
    @Mock
    private CardDrawView mockCardDrawView;
    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockCardDrawView).updateCanvas(notNull());
        doThrow(IllegalArgumentException.class).when(mockCardDrawView).updateCanvas(null);
        cardController = new CardController(mockCardDrawView,mockGameScreen);
    }
    @Test
    void testExtractCardsFromServerString() {
        String serverString = "0-ROBOTER-2-WASSER;1-PFLANZE-3-ENERGIE;2-RAUMANZUG-4-PLANUNG";
        cardController.extractCardsFromServerString(serverString);
        CardCombination[] combinations=cardController.getCurrentCombination();
        assertEquals(FieldCategory.ROBOT, combinations[0].getCurrentSymbol());
        assertEquals(FieldValue.TWO, combinations[0].getCurrentNumber());
        assertEquals(FieldCategory.WATER, combinations[0].getNextSymbol());

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
    void testUpdateCanvasThrowsExceptionWhenNullParameter() throws IllegalAccessException, NoSuchFieldException {
        Field field = cardController.getClass().getDeclaredField("currentCombination");
        field.setAccessible(true);
        field.set(cardController, null);
        assertThrows(IllegalArgumentException.class,()->cardController.displayCurrentCombination());
    }
    @Test
    void testUpdateCanvasDoesNotThrowExceptionWhenNonNullParameter() throws NoSuchFieldException, IllegalAccessException {
        Field field = cardController.getClass().getDeclaredField("currentCombination");
        field.setAccessible(true);
        CardCombination[] combinations= {
                new CardCombination(FieldCategory.ENERGY,FieldCategory.ENERGY,FieldValue.ELEVEN),
                new CardCombination(FieldCategory.ENERGY,FieldCategory.ENERGY,FieldValue.ELEVEN),
                new CardCombination(FieldCategory.ENERGY,FieldCategory.ENERGY,FieldValue.ELEVEN)};
        field.set(cardController, combinations);
        assertDoesNotThrow(()->cardController.displayCurrentCombination());
    }



}
