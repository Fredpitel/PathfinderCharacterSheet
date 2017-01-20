package Controller.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import net.sf.json.JSONObject;

public class CharClass {
    public String className;
    public String abb;
    public int hitDie;
    public Progression attackProg;
    public Progression fortProg;
    public Progression refProg;
    public Progression wilProg;
    
    private final BooleanProperty isFavoredClass;
    
    public CharClass(JSONObject jsonClass, Character mainChar) {
        className = jsonClass.getString("className");
        abb = jsonClass.getString("abb");
        hitDie = jsonClass.getInt("hitdie");
        isFavoredClass = new SimpleBooleanProperty();
        isFavoredClass.bind(mainChar.getFavoredClassProperty().isEqualTo(className));
        attackProg = new Progression(jsonClass.getString("attackProg"));
        fortProg = new Progression(jsonClass.getString("fortProg"));
        refProg = new Progression(jsonClass.getString("refProg"));
        wilProg = new Progression(jsonClass.getString("wilProg"));
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
