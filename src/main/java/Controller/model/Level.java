package Controller.model;

import Controller.model.FavoredBonus.FavoredBonus;
import Controller.model.FavoredBonus.HitPointBonus;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public final class Level {
    static int levelCounter = 1;
    
    public CharClass charClass;
    public int levelNumber;

    private final IntegerProperty hpGained = new SimpleIntegerProperty(1);
    private final ObjectProperty<FavoredBonus> favoredBonus;
    
    public Level(CharClass charClass) {
        this.charClass = charClass;
        levelNumber = levelCounter++;
        if(levelNumber == 1) setHpGained(charClass.hitDie);
        favoredBonus = new SimpleObjectProperty(new HitPointBonus());
    }
    
    public int getHpBonus(Character mainChar) {
        if(mainChar.getFavoredClass().equals(charClass)) {
            return favoredBonus.get().getHpBonus();
        }
        
        return 0;
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
    
    public FavoredBonus getFavoredBonus() {
        return favoredBonus.get();
    }
    
    public void setFavoredBonus(FavoredBonus bonus) {
        favoredBonus.set(bonus);
    }
    
    public ObjectProperty<FavoredBonus> getFavoredBonusProperty() {
        return favoredBonus;
    }
}
