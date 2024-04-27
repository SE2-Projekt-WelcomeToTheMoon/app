package com.example.se2_projekt_app.game;
import com.example.se2_projekt_app.enums.Element;
import com.example.se2_projekt_app.enums.FieldValue;


public class CardController {
    private CardCombination[] currentCombination;
    /***
     * Gets the server String which is composed as follows:
     * data inside the combinations is split by - and ordered CombinationNumber-CurrentSymbol-CurrentNumber-NextSymbol
     * The Combinations themselves are split by ;
     * Then saves the extracted CardDraw into CurrentCombination
     * @param serverString The String with the currentDrawData from the Server
     */
    public void extractCardsFromServerString(String serverString){
        if(serverString.isEmpty())throw new IllegalArgumentException("String cannot be empty");
        String[] splitString =serverString.split(";");
        if(splitString.length!=3)throw new IllegalArgumentException("String must Contain data about 3 Combinations");
        CardCombination[] combinations=new CardCombination[3];
        for(String combinationString:splitString){
            String[] combinationStringParts=combinationString.split("-");
            int combinationNumber=Integer.parseInt(combinationStringParts[0]);
            Element currentSymbol=Element.getSymbolAndTranslate(combinationStringParts[1]);
            FieldValue currentNumber=getCurrentNumberFromInt(Integer.parseInt(combinationStringParts[2]));
            Element nextSymbol=Element.getSymbolAndTranslate(combinationStringParts[3]);
            combinations[combinationNumber]=new CardCombination(currentSymbol,nextSymbol,currentNumber);
        }
        currentCombination=combinations;
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


    public CardCombination[] getCurrentCombination() {
        return currentCombination;
    }
}
