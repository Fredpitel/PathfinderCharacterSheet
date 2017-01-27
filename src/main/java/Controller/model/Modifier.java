package Controller.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Modifier {
    public static enum modifierTypes {ALCHEMICAL, ARMOR, BAB, CIRCUMSTANCE, COMPETENCE, DEFLECTION, DODGE, ENHANCEMENT, 
                                      INHERENT, INSIGHT, LUCK, MORALE, NATURAL_ARMOR, PROFANE, RACIAL, RESISTANCE, 
                                      SACRED, SHIELD, SIZE, TRAIT, UNTYPED};
    
    public final IntegerProperty value;
    private final StringProperty source;
    public final boolean isStacking;
    
    public Modifier(int value, String source, boolean isStacking) {
        this.value = new SimpleIntegerProperty(value);
        this.source = new SimpleStringProperty(source);
        this.isStacking = isStacking;
    }
    
    public int getValue() {
        return value.get();
    }
    
    public void setValue(int value) {
        this.value.set(value);
    }
    
    public IntegerProperty getValueProperty() {
        return value;
    }
}
