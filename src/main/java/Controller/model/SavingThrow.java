package Controller.model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class SavingThrow {
    private final String name;
    private final IntegerProperty value;
    private final ObservableMap<Modifier.modifierTypes, Modifier> modifiers;
    
    public SavingThrow(String name, IntegerProperty scoreModifierProperty, ObservableMap classMap) {
        this.name = name;
        value = new SimpleIntegerProperty();
        modifiers = FXCollections.observableMap(new HashMap());
        
        value.bind(scoreModifierProperty
                   .add(Bindings.createIntegerBinding(() -> getNewSave(classMap), classMap))
                   .add(Bindings.createIntegerBinding(() -> modifiers.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getValue().getValue())), modifiers)));
    }
    
    public IntegerProperty getValueProperty() {
        return value;
    }
    
    public int getValue() {
        return value.get();
    }
    
    public int getNewSave(ObservableMap<CharClass, Integer> classMap) {
        int total = 0;
        
        for(Entry<CharClass, Integer> entry : classMap.entrySet()){
            switch(entry.getKey().getProg(name)) {
                case SLOW:
                    total += (Math.floor(0.34 * entry.getValue()));
                    break;
                case FAST:
                    total += (Math.floor(2 + (entry.getValue()/2)));
                    break;
            }
        }
        
        return total;
    }
}
