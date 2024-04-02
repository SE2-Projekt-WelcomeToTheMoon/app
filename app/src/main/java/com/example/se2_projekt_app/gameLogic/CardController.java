package com.example.se2_projekt_app.gameLogic;

import android.annotation.SuppressLint;

public class CardController {

    public CardStack cardStack;
    public int currentPosition;
    public PlayingCard[] nextCards;
    public PlayingCard[] currentCards;

    public CardController() {
        this.cardStack = new CardStack();
        this.currentPosition = 0;
        currentCards = getCardsAtPosition(currentPosition);
        nextCards = getCardsAtPosition(currentPosition + 1);
    }

    @SuppressLint("DefaultLocale")
    public String showCurrentDraw() {
        return String.format("The Current Card Draw at position %d is:\nCard 1 has symbol %s and the next card is %d with symbol %s" +
                        "\nCard 2 has symbol %s and the next card is %d with symbol %s" +
                        "\nCard 3 has symbol %s and the next card is %d with symbol %s",
                currentPosition, currentCards[0].symbol.toString(), nextCards[0].number, nextCards[0].symbol.toString(),
                currentCards[1].symbol.toString(), nextCards[1].number, nextCards[1].symbol.toString(),
                currentCards[2].symbol.toString(), nextCards[2].number, nextCards[2].symbol.toString());
    }

    public PlayingCard[] getCardsAtPosition(int position) {
        if (position > 21) throw new IllegalArgumentException("Position cannot exceed 21");
        PlayingCard[] cards = new PlayingCard[3];
        cards[0] = cardStack.getCards().get(position);
        cards[1] = cardStack.getCards().get(position + 21);
        cards[2] = cardStack.getCards().get(position + 42);
        return cards;
    }

    public void drawNextCard() {
        currentPosition++;
        if (currentPosition == 20) {
            currentCards = getCardsAtPosition(currentPosition);
            cardStack.shuffleDeck();
            currentPosition = 0;
            nextCards = getCardsAtPosition(currentPosition);
        } else {
            currentCards = getCardsAtPosition(currentPosition);
            nextCards = getCardsAtPosition(currentPosition + 1);
        }
    }
}