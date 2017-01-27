package Controller.model;

import Controller.model.Modifier.modifierTypes;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

public class Race {
    private final StringProperty raceName;
    
    public Race(String raceName, ArrayList<Pair> modifiers, Character mainChar){
        this.raceName = new SimpleStringProperty(raceName);
        for(Pair<String, Integer> modifier : modifiers) {
            mainChar.getAbilityScore(modifier.getKey()).addModifier(modifierTypes.RACIAL, modifier.getValue(), "charRace", false);
        }
    }
}
