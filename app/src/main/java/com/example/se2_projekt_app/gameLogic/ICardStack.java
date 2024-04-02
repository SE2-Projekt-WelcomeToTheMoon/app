package com.example.se2_projekt_app.gameLogic;
public interface ICardStack {

    int[] cardArray = {
            1, 1, 2, 2, 14, 14, 15, 15, // 2 cards with 1/2/14/15
            3, 3, 3, 13, 13, 13, // 3 cards with 3/13
            4, 4, 4, 4, 12, 12, 12, 12, // 4 cards with 4/12
            5, 5, 5, 5, 5, 11, 11, 11, 11, 11, // 5 cards with 5/11
            6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 9, 9, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, // 6 cards with 6/7/9/10
            8, 8, 8, 8, 8, 8, 8 // 7 cards with 8
    };
    CardSymbolEnum[] symbolArray=initializeCardSymbols();
    static CardSymbolEnum[] initializeCardSymbols(){
        CardSymbolEnum[] cardSymbols = new CardSymbolEnum[63]; // Total number of cards

        addCards(cardSymbols, CardSymbolEnum.ROBOT, 0, 14); //14 Robot
        addCards(cardSymbols, CardSymbolEnum.ENERGY, 14, 28); //14 Energy
        addCards(cardSymbols, CardSymbolEnum.PLANT, 28, 42); //14 Plant
        addCards(cardSymbols, CardSymbolEnum.WATER, 42, 49); //7 Water
        addCards(cardSymbols, CardSymbolEnum.ASTRONAUT, 49, 56); //7 Astronaut
        addCards(cardSymbols, CardSymbolEnum.CALENDER, 56, 63); //7 Calender

        return cardSymbols;
    }
    static void addCards(CardSymbolEnum[] cardSymbols, CardSymbolEnum symbol, int start, int end) {
        for (int i = start; i < end; i++) {
            cardSymbols[i] = symbol;
        }
    }
}
