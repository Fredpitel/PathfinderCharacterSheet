package Controller.model.ModifiableObject;

import Controller.model.Character;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AC extends ModifiableObject {
    private final IntegerProperty baseAc;
    private final IntegerProperty totalAc;
    
    public AC(Character mainChar) {
        super(mainChar.modifiers);
        baseAc = new SimpleIntegerProperty(10);
        totalAc = new SimpleIntegerProperty();
        
        totalAc.bind(baseAc
                    .add(mainChar.getAbilityScore("DEXTERITY").getScoreModifierProperty())
                    .add(modValue));
    }
    
    public IntegerProperty getTotalAcProperty() {
        return totalAc;
    }
    
    public IntegerProperty getBaseAcProperty() {
        return baseAc;
    }
}
