package Controller.model;

import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class AbilityScore {
    private final IntegerProperty baseValue;
    private final IntegerProperty currentValue;
    private final IntegerProperty scoreModifier;
    private final ObservableMap<Modifier.modifierTypes, Modifier> modifiers;
    
    public AbilityScore(int value) {
        this.baseValue = new SimpleIntegerProperty(value);
        currentValue = new SimpleIntegerProperty();
        modifiers = FXCollections.observableMap(new HashMap());
        scoreModifier = new SimpleIntegerProperty();
        
        currentValue.bind(baseValue.add(Bindings.createIntegerBinding(() -> modifiers.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getValue().getValue())), modifiers)));
        scoreModifier.bind(Bindings.createIntegerBinding(() -> (int) Math.floor((getCurrentValue() - 10) / 2), this.currentValue));
    }
    
    public IntegerProperty getCurrentValueProperty() {
        return currentValue;
    }
    
    public int getCurrentValue() {
        return currentValue.get();
    }
    
    public IntegerProperty getBaseValueProperty() {
        return baseValue;
    }
    
    public int getBaseValue() {
        return baseValue.get();
    }
    
    public void setBaseValue(int value) {
        this.baseValue.set(value);
    }
    
    public IntegerProperty getScoreModifierProperty() {
        return scoreModifier;
    }
    
    public void addModifier(Modifier.modifierTypes type, int value, String source, boolean isStacking) {
        if(modifiers.get(type) == null || (!modifiers.get(type).isStacking && modifiers.get(type).getValue() < value)) {
            modifiers.put(type, new Modifier(value, source, isStacking));
        } else {
            Modifier modif = modifiers.get(type);
            modif.setValue(modif.getValue() + value);
            modifiers.replace(type, modif);
        }
    }
}
