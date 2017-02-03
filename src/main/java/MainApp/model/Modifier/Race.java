package MainApp.model.Modifier;

import java.util.ArrayList;
import javafx.beans.property.StringProperty;

public class Race extends ModifyingObject{
    public ArrayList<String> abilities = new ArrayList();
    public ArrayList<String> languages = new ArrayList();
    
    public Race(String raceName) {
        this.name.set(raceName);
    }
    
    public StringProperty getRaceNameProperty() {
        return name;
    }
    
    public void setRace(String raceName, ArrayList<String> abilities, ArrayList<String> languages) {
        name.set(raceName);
        this.abilities = abilities;
        this.languages = languages;
    }

}
