package Controller.model;

import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class AC {
    private final IntegerProperty baseAc;
    private final IntegerProperty totalAc;
    private final ObservableMap<Modifier.modifierTypes, Modifier> modifiers;
    
    public AC(IntegerProperty dexScore) {
        baseAc = new SimpleIntegerProperty(10);
        totalAc = new SimpleIntegerProperty();
        modifiers = FXCollections.observableMap(new HashMap());
        
        totalAc.bind(baseAc.add(dexScore)
                           .add(Bindings.createIntegerBinding(() -> modifiers.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getValue().getValue())), modifiers)));
    }
    
    public IntegerProperty getTotalAcProperty() {
        return totalAc;
    }
    
    public IntegerProperty getBaseAcProperty() {
        return baseAc;
    }
}
