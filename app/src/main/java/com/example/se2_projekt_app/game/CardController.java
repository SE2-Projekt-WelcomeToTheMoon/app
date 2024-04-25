package com.example.se2_projekt_app.game;
import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;

public class CardController {

    /***
     * Gets the server String which is composed as follows:
     * data inside the combinations is split by - and ordered CombinationNumber-CurrentSymbol-CurrentNumber-NextSymbol
     * The Combinations themselves are split by ;
     * @param serverString The String with the currentDrawData from the Server
     * @return The Three current CardCombinations
     */
    public CardCombination[] extractCardsFromServerString(String serverString){
        if(serverString.isEmpty())throw new IllegalArgumentException("String cannot be empty");
        String[] splitString =serverString.split(";");
        if(splitString.length!=3)throw new IllegalArgumentException("String must Contain data about 3 Combinations");
        CardCombination[] combinations=new CardCombination[3];
        for(String combinationString:splitString){
            String[] combinationStringParts=combinationString.split("-");
            int combinationNumber=Integer.parseInt(combinationStringParts[0]);
            Element currentSymbol=getSymbolAndTranslate(combinationStringParts[1]);
            FieldValue currentNumber=getCurrentNumberFromInt(Integer.parseInt(combinationStringParts[2]));
            Element nextSymbol=getSymbolAndTranslate(combinationStringParts[3]);
            combinations[combinationNumber]=new CardCombination(currentSymbol,nextSymbol,currentNumber);
        }
        return combinations;
    }

    /***
     * The enum on the Serverside has german Names while the client side has english names, so this takes
     * the String with the german name and finds the corresponding element
     * @param element Element name in german from serverside
     * @return corresponding element
     */
    public Element getSymbolAndTranslate(String element) {

        switch(element) {
            case "ROBOTER":
                return Element.ROBOT;
            case "WASSER":
                return Element.WATER;
            case "PFLANZE":
                return Element.PLANT;
            case "ENERGIE":
                return Element.ENERGY;
            case "RAUMANZUG":
                return Element.SPACESUIT;
            case "PLANNUNG":
                return Element.PLANNING;
            case "ANYTHING":
                return Element.WILDCARD;
            default:
                throw new IllegalArgumentException("Not part of recognized symbols");
        }
    }

    /***
     * Gets corresponding fieldValue from number
     * @param value int to be converted
     * @return Corresponding FieldValue
     */
    public FieldValue getCurrentNumberFromInt(int value) {
        for (FieldValue fieldValue : FieldValue.values()) {
            if (fieldValue.getValue() == value) {
                return fieldValue;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }




}
