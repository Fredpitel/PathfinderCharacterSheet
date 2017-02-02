package Controller.model;

import Controller.model.ModifiableObject.AbilityScore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class AbilityScores {
    private static final List<String> ABILITIES = Arrays.asList("STRENGTH", "DEXTERITY", "CONSTITUTION", "INTELLIGENCE", "WISDOM", "CHARISMA");
    
    private final ObservableMap<String, AbilityScore> abilityScoreMap;
    
    public AbilityScores(ObservableList modifiers) {
        abilityScoreMap = FXCollections.observableMap(new HashMap());
        for(String ability : ABILITIES) {
            abilityScoreMap.put(ability, new AbilityScore(modifiers));
        }
    }
    
    public AbilityScore getAbilityScore(String ability) {
        return abilityScoreMap.get(ability);
    }
}
