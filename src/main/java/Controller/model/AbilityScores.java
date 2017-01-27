package Controller.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class AbilityScores {
    private static final List<String> ABILITIES = Arrays.asList("STRENGTH", "DEXTERITY", "CONSTITUTION", "INTELLIGENCE", "WISDOM", "CHARISMA");
    
    private final ObservableMap<String, AbilityScore> abilityScoreMap;
    
    public AbilityScores() {
        abilityScoreMap = FXCollections.observableMap(new HashMap());
        for(String ability : ABILITIES) {
            abilityScoreMap.put(ability, new AbilityScore(10));
        }
    }
    
    public AbilityScore getAbilityScore(String ability) {
        return abilityScoreMap.get(ability);
    }
}
