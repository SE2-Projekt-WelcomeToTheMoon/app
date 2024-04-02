package com.example.se2_projekt_app.gameLogic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardStack implements ICardStack{
    private ArrayList<PlayingCard> cards;

    public CardStack(){
        this.cards=createCardList(ICardStack.cardArray,ICardStack.symbolArray);
    }
    /***
     * creates a list of PlayingCards with randomly assigned numbers and symbols from the input
     * @param numbers List of numbers to assign to this list
     * @param symbols List of symbols to assign to this list
     * @return an Arraylist with PlayingCards with randomized symbol and number combinations
     */
    public ArrayList<PlayingCard> createCardList(int[] numbers, CardSymbolEnum[] symbols) {
        if (numbers == null || symbols == null || numbers.length == 0 || symbols.length == 0)throw new IllegalArgumentException("Numbers and symbols arrays must not be null or empty.");
        if(numbers.length!= symbols.length)throw new IllegalArgumentException("Number and Symbol array must be of same length");


        List<Integer> list = new ArrayList<>();
        for (int number : numbers) {
            Integer integer = number;
            list.add(integer);
        }
        List<Integer> numberList = new ArrayList<>(list);
        List<CardSymbolEnum> symbolList = new ArrayList<>(Arrays.asList(symbols));

        Collections.shuffle(numberList);
        Collections.shuffle(symbolList);

        ArrayList<PlayingCard> cardList = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++) {
            cardList.add(new PlayingCard(symbolList.get(i), numberList.get(i)));
        }

        return cardList;
    }

    public ArrayList<PlayingCard> getCards() {
        return cards;
    }
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }
}

