package Controller.model.ModifiableObject;

import Controller.model.Modifier.Modifier;
import Controller.model.Modifier.Modifier.modifierTypes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public abstract class ModifiableObject {
    public final IntegerProperty modValue;
    private Map<modifierTypes, ArrayList<Modifier>> activeModifiers;
    
    public ModifiableObject(ObservableList modifiers) {
        modValue = new SimpleIntegerProperty(0);
        activeModifiers = new HashMap();
        modValue.bind(Bindings.createIntegerBinding(() -> bindValue(modifiers), modifiers));
    }
    
    private int bindValue(ObservableList<Modifier> modifiers) {
        int total = 0;
        
        updateModMap(modifiers);
      
        for(Entry<modifierTypes, ArrayList<Modifier>> entry : activeModifiers.entrySet()) {
            ArrayList<Modifier> modList = entry.getValue();
            for(Modifier mod : modList) {
                total += mod.value;
            }
        }
        
        return total;
    }
    
    private void updateModMap(ObservableList<Modifier> modifiers) {
        activeModifiers = new HashMap();
        for(Modifier mod : modifiers) {
            if(mod.target == this) {
                ArrayList modList = activeModifiers.get(mod.type);
                if(modList == null) modList = new ArrayList();
                if(modList.isEmpty() || (mod.isStacking && notSameSource(mod, modList))) {
                    modList.add(mod);
                } else {
                    modList.add(getGreaterMod(mod, modList));
                }
                
                activeModifiers.put(mod.type, modList);
            }
        }
    }
    
    private boolean notSameSource(Modifier mod, ArrayList<Modifier> modList) {
        for(Modifier activeMod : modList) {
            if(activeMod.source == mod.source) return false;
        }
        
        return true;
    }
    
    private Modifier getGreaterMod(Modifier mod, ArrayList<Modifier> modList) {
        Modifier greaterMod = mod;
        
        for(Modifier activeMod : modList) {
            if (Math.abs(activeMod.value) > Math.abs(greaterMod.value)) {
                greaterMod = activeMod;
            }
        }  
        
        return greaterMod;
    }
}
