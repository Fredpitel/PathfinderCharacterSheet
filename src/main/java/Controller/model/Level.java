package Controller.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public final class Level {
    static int levelCounter = 1;
    
    public CharClass charClass;
    public int levelNumber;

    private final IntegerProperty hpGained = new SimpleIntegerProperty(1);
    private final BooleanProperty hitPointBonus;
    
    public Level(CharClass charClass) {
        this.charClass = charClass;
        levelNumber = levelCounter++;
        if(levelNumber == 1) setHpGained(charClass.hitDie);
        hitPointBonus = new SimpleBooleanProperty(true);
    }
    
    public void switchBonus(){
        hitPointBonus.set(!hitPointBonus.get());
    }
    
    public int getHpGained() {
        return hpGained.get();
    }
    
    public void setHpGained(int hp) {
        hpGained.set(hp);
    }
    
    public IntegerProperty getHpGainedProperty() {
        return hpGained;
    }
    
    public BooleanProperty getHitPointBonusProperty() {
        return hitPointBonus;
    }
}
