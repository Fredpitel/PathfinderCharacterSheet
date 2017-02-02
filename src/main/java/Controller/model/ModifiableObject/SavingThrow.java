package Controller.model.ModifiableObject;

import Controller.model.CharClass;
import java.util.Map.Entry;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class SavingThrow extends ModifiableObject {
    private final String name;
    private final IntegerProperty value;
    
    public SavingThrow(String name, IntegerProperty scoreModifierProperty, ObservableMap classMap, ObservableList modifiers) {
        super(modifiers);
        this.name = name;
        value = new SimpleIntegerProperty();
        
        value.bind(scoreModifierProperty
                   .add(Bindings.createIntegerBinding(() -> getNewSave(classMap), classMap))
                   .add(modValue));
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
