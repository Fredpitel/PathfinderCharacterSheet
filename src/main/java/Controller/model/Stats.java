package Controller.model;

import java.util.HashMap;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Stats {
    private final ObservableMap<String, IntegerProperty> statMap;
    private final ObservableMap<String, IntegerProperty> statModifierMap;

    public Stats() {
        statMap = FXCollections.observableMap(new HashMap());
        statMap.put("STRENGTH", new SimpleIntegerProperty(10));
        statMap.put("DEXTERITY", new SimpleIntegerProperty(10));
        statMap.put("CONSTITUTION", new SimpleIntegerProperty(10));
        statMap.put("INTELLIGENCE", new SimpleIntegerProperty(10));
        statMap.put("WISDOM", new SimpleIntegerProperty(10));
        statMap.put("CHARISMA", new SimpleIntegerProperty(10));
        
        statModifierMap = FXCollections.observableMap(new HashMap());
        statModifierMap.put("STRENGTH", createNewProperty("STRENGTH"));
        statModifierMap.put("DEXTERITY", createNewProperty("DEXTERITY"));
        statModifierMap.put("CONSTITUTION", createNewProperty("CONSTITUTION"));
        statModifierMap.put("INTELLIGENCE", createNewProperty("INTELLIGENCE"));
        statModifierMap.put("WISDOM", createNewProperty("WISDOM"));
        statModifierMap.put("CHARISMA", createNewProperty("CHARISMA"));
    }
    
    private SimpleIntegerProperty createNewProperty(String stat) {
        SimpleIntegerProperty property = new SimpleIntegerProperty(0);
        property.bind(Bindings.createIntegerBinding(() -> (int) Math.floor((statMap.get(stat).get() - 10) / 2), statMap));
        return property;
    }
    
    public void add(String stat, int pointsAdded) {   
        statMap.put(stat, new SimpleIntegerProperty(statMap.get(stat).get() + pointsAdded));
    }
    
    public void remove(String stat) {
        statMap.put(stat, new SimpleIntegerProperty(statMap.get(stat).get() - 1));
    }
    
    public IntegerProperty getStat(String stat) {
        return statMap.get(stat);
    }
    
    public IntegerProperty getStatModifier(String stat) {
        return statModifierMap.get(stat);
    }
}