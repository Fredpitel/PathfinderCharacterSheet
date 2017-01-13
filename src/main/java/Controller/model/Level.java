package Controller.model;

import Controller.model.FavoredBonus.FavoredBonus;
import Controller.model.FavoredBonus.FavoredBonusFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public final class Level {
    static int levelCounter = 1;
    
    private final BooleanProperty isFavoredClass;
    
    public String className;
    public int levelNumber;
    public int hitDie;
    public int hpGained = 1;
    public FavoredBonus favoredBonus;

    public Level(String className, String favoredClass, int hitDie) {
        this.className = className;
        levelNumber = levelCounter++;
        if(levelNumber == 1) hpGained = hitDie;
        this.hitDie = hitDie;
        isFavoredClass = new SimpleBooleanProperty(className.equals(favoredClass));
        
        if(getIsFavoredClass()) {
            favoredBonus = new FavoredBonusFactory().createBonus("+1 Hit Point");   
        }
    }
    
    public void switchBonus(Character mainChar, String bonus) {
        favoredBonus.unModifyMainChar(mainChar);
        favoredBonus = new FavoredBonusFactory().createBonus(bonus);
        favoredBonus.modifyMainChar(mainChar);
    }
    
    public boolean getIsFavoredClass() {
        return isFavoredClass.get();
    }
    
    public void setIsFavoredClass(boolean isFavored) {
        isFavoredClass.set(isFavored);
    }
    
    public BooleanProperty getIsFavoredClassProperty() {
        return isFavoredClass;
    }
}
