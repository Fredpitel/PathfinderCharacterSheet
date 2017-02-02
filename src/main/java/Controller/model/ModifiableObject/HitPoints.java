package Controller.model.ModifiableObject;

import Controller.model.Character;
import Controller.model.Level;
import Controller.model.Modifier.Modifier;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class HitPoints extends ModifiableObject {
    private final IntegerProperty maxHp;
    private final IntegerProperty currentHp;
    private final IntegerProperty bonusHp;
    private final IntegerProperty damageRecieved;
    private final ObservableMap<Modifier.modifierTypes, Modifier> modifiers;
    
    public HitPoints(Character mainChar) {
        super(mainChar.modifiers);
        maxHp = new SimpleIntegerProperty();
        currentHp = new SimpleIntegerProperty();
        bonusHp = new SimpleIntegerProperty();
        damageRecieved = new SimpleIntegerProperty();
        modifiers = FXCollections.observableMap(new HashMap());
        
        currentHp.bind(maxHp.subtract(damageRecieved));
        bonusHp.bind(Bindings.createIntegerBinding(() -> mainChar.getCharLevels().stream().collect(Collectors.summingInt((level) -> level.getHitPointBonusProperty().get() ? 1 : 0)), mainChar.getCharLevels(), mainChar.getFavoredClassProperty()));
        maxHp.bind((mainChar.getAbilityScore("CONSTITUTION").getScoreModifierProperty().multiply(Bindings.size(mainChar.getCharLevels())))
                   .add(bonusHp)
                   .add(Bindings.createIntegerBinding(() -> mainChar.getCharLevels().stream().collect(Collectors.summingInt(Level::getHpGained)), mainChar.getCharLevels())));
    }
    
    public IntegerProperty getMaxHpProperty() {
        return maxHp;
    }
    
    public int getMaxHp() {
        return maxHp.get();
    }
    
    public IntegerProperty getCurrentHpProperty() {
        return currentHp;
    }
    
    public int getCurrentHp() {
        return currentHp.get();
    }
    
    public IntegerProperty getBonusHpProperty() {
        return bonusHp;
    }
    
    public void setBonusHitPoint(int bonus) {
        bonusHp.set(bonus);
    }
}
