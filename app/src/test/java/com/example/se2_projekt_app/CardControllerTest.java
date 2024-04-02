package com.example.se2_projekt_app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.se2_projekt_app.gameLogic.CardController;
import com.example.se2_projekt_app.gameLogic.PlayingCard;

public class CardControllerTest {
    private CardController controller=new CardController();

    @BeforeEach
    void setUp() {
        controller = new CardController();
    }

    @Test
    void showCurrentDraw() {
        String expected = "The Current Card Draw at position 0 is:\n" +
                "Card 1 has symbol " + controller.currentCards[0].symbol.toString() + " and the next card is " +
                controller.nextCards[0].number + " with symbol " + controller.nextCards[0].symbol.toString() + "\n" +
                "Card 2 has symbol " + controller.currentCards[1].symbol.toString() + " and the next card is " +
                controller.nextCards[1].number + " with symbol " + controller.nextCards[1].symbol.toString() + "\n" +
                "Card 3 has symbol " + controller.currentCards[2].symbol.toString() + " and the next card is " +
                controller.nextCards[2].number + " with symbol " + controller.nextCards[2].symbol.toString();

        assertEquals(expected, controller.showCurrentDraw());
    }

    @Test
    void getCardsAtPosition_ValidPosition() {
        PlayingCard[] cards = controller.getCardsAtPosition(0);
        assertNotNull(cards);
        assertEquals(3, cards.length);
        assertNotNull(cards[0]);
        assertNotNull(cards[1]);
        assertNotNull(cards[2]);
    }

    @Test
    void getCardsAtPosition_InvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> controller.getCardsAtPosition(22));
    }

    @Test
    void drawNextCard_PositionChange() {
        int initialPosition = controller.currentPosition;
        controller.drawNextCard();
        assertEquals(initialPosition + 1, controller.currentPosition);
    }

    @Test
    void drawNextCard_PositionReset() {
        controller.currentPosition = 19;
        controller.drawNextCard();
        assertEquals(0, controller.currentPosition);
    }

    /***
     * This test asserts true as long as one of the current cards is different, which still gives a roughly 1/63^3 chance of randomly failing
     */
    @Test
    void drawNextCard_ShuffleDeck() {
        PlayingCard[] currentCardsTest= controller.currentCards;
        controller.currentPosition = 19;
        controller.drawNextCard();
        if(currentCardsTest[0] != controller.currentCards[0]){
            assertTrue(true);
            return;
        }
        if(currentCardsTest[1] != controller.currentCards[1]) {
            assertTrue(true);
            return;
        }
        if(currentCardsTest[2] != controller.currentCards[2]) {
            assertTrue(true);
            return;
        }
        fail();

    }
}
