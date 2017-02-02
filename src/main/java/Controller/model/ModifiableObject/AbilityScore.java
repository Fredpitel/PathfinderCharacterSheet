package Controller.model.ModifiableObject;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class AbilityScore extends ModifiableObject {
    private final IntegerProperty baseValue;
    private final IntegerProperty currentValue;
    private final IntegerProperty scoreModifier;
    
    public AbilityScore(ObservableList modifiers) {
        super(modifiers);
        this.baseValue = new SimpleIntegerProperty(10);
        currentValue = new SimpleIntegerProperty();
        scoreModifier = new SimpleIntegerProperty();
        
        currentValue.bind(baseValue.add(modValue));
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
}
