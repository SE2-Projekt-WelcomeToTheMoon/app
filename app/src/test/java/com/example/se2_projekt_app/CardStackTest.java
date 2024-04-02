package com.example.se2_projekt_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.se2_projekt_app.gameLogic.CardStack;
import com.example.se2_projekt_app.gameLogic.CardSymbolEnum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardStackTest {
    private CardStack cardStack=new CardStack();

    @BeforeEach
    void setUp() {
        cardStack = new CardStack();
    }

    @Test
    void createCardList_InvalidInputs_NullNumbers() {
        CardSymbolEnum[] symbols = {CardSymbolEnum.ROBOT, CardSymbolEnum.ENERGY, CardSymbolEnum.PLANT};
        assertThrows(IllegalArgumentException.class, () -> cardStack.createCardList(null, symbols));
    }

    @Test
    void createCardList_InvalidInputs_NullSymbols() {
        int[] numbers = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> cardStack.createCardList(numbers, null));
    }

    @Test
    void createCardList_InvalidInputs_EmptyNumbers() {
        int[] numbers = {};
        CardSymbolEnum[] symbols = {CardSymbolEnum.ROBOT, CardSymbolEnum.ENERGY, CardSymbolEnum.PLANT};
        assertThrows(IllegalArgumentException.class, () -> cardStack.createCardList(numbers, symbols));
    }

    @Test
    void createCardList_InvalidInputs_EmptySymbols() {
        int[] numbers = {1, 2, 3};
        CardSymbolEnum[] symbols = {};
        assertThrows(IllegalArgumentException.class, () -> cardStack.createCardList(numbers, symbols));
    }

    @Test
    void createCardList_InvalidInputs_DifferentLengths() {
        int[] numbers = {1, 2, 3};
        CardSymbolEnum[] symbols = {CardSymbolEnum.ROBOT, CardSymbolEnum.ENERGY};
        assertThrows(IllegalArgumentException.class, () -> cardStack.createCardList(numbers, symbols));
    }

    @Test
    void createCardList_ValidInputs() {
        int[] numbers = {1, 2, 3};
        CardSymbolEnum[] symbols = {CardSymbolEnum.ROBOT, CardSymbolEnum.ENERGY, CardSymbolEnum.PLANT};
        assertEquals(3, cardStack.createCardList(numbers, symbols).size());
    }

    @Test
    void shuffleDeck() {
        // Ensure that shuffle does not change the order of cards
        CardStack originalStack = new CardStack();
        CardStack shuffledStack = new CardStack();
        shuffledStack.shuffleDeck();
        assertNotEquals(originalStack.getCards(), shuffledStack.getCards());
    }
}


