package com.example.se2_projekt_app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.se2_projekt_app.gameLogic.CardSymbolEnum;
import com.example.se2_projekt_app.gameLogic.PlayingCard;

import org.junit.jupiter.api.Test;

public class PlayingCardTest {
    @Test
    void testToString() {
        PlayingCard card = new PlayingCard(CardSymbolEnum.ROBOT, 5);
        String expected = "Card has Symbol ROBOT and Number 5";
        assertEquals(expected, card.toString());
    }

    @Test
    void testGetSymbol() {
        PlayingCard card = new PlayingCard(CardSymbolEnum.ENERGY, 3);
        assertEquals(CardSymbolEnum.ENERGY, card.symbol);
    }

    @Test
    void testGetNumber() {
        PlayingCard card = new PlayingCard(CardSymbolEnum.PLANT, 8);
        assertEquals(8, card.number);
    }
}
