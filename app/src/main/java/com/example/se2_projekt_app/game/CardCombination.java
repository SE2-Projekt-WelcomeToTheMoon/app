package com.example.se2_projekt_app.game;


import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

public class CardCombination {
    private Element currentSymbol;
    private Element nextSymbol;
    private FieldValue currentNumber;
    public CardCombination(Element currentSymbol, Element nextSymbol, FieldValue currentNumber){

        this.currentSymbol= currentSymbol;
        this.nextSymbol= nextSymbol;
        this.currentNumber= currentNumber;

    }

    public Element getCurrentSymbol() {
        return currentSymbol;
    }

    public Element getNextSymbol() {
        return nextSymbol;
    }

    public FieldValue getCurrentNumber() {
        return currentNumber;
    }

}
