package com.example.se2_projekt_app.game;
import com.example.se2_projekt_app.enums.FieldCategory;
import com.example.se2_projekt_app.enums.FieldValue;
import com.example.se2_projekt_app.views.CardDrawView;

import lombok.Getter;

/***
 * The class responsible for controlling cards. Gets CardDrawView in constructor
 */
@Getter
public class CardController {
    @Getter
    private CardCombination[] currentCombination;
    private final CardDrawView cardDrawView;

    public CardController(CardDrawView cardDrawView) {
        this.cardDrawView = cardDrawView;
    }

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
            FieldCategory currentSymbol=FieldCategory.getSymbolAndTranslate(combinationStringParts[1]);
            FieldValue currentNumber=FieldValue.getCurrentNumberFromInt(Integer.parseInt(combinationStringParts[2]));
            FieldCategory nextSymbol=FieldCategory.getSymbolAndTranslate(combinationStringParts[3]);
            combinations[combinationNumber]=new CardCombination(currentSymbol,nextSymbol,currentNumber);
        }
        currentCombination=combinations;
    }

    /***
     * Displays current Combination
     */
    public void displayCurrentCombination(){
        cardDrawView.updateCanvas(currentCombination);

    }


}
