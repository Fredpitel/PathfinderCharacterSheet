package MainApp.model.Modifier;

import MainApp.model.ModifiableObject.ModifiableObject;

public class Modifier {
    public static enum modifierTypes {ALCHEMICAL, ARMOR, BAB, CIRCUMSTANCE, COMPETENCE, DEFLECTION, DODGE, ENHANCEMENT, 
                                      INHERENT, INSIGHT, LUCK, MORALE, NATURAL_ARMOR, PROFANE, RACIAL, RESISTANCE, 
                                      SACRED, SHIELD, SIZE, TRAIT, UNTYPED};
    
    public final int value;
    public final ModifyingObject source;
    public modifierTypes type;
    public ModifiableObject target;
    public final boolean isStacking;
    
    public Modifier(int value, ModifyingObject source, ModifiableObject target, modifierTypes type, boolean isStacking) {
        this.value = value;
        this.source = source;
        this.target = target;
        this.type = type;
        this.isStacking = isStacking;
    }
}
