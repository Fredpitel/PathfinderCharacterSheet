package MainApp.model.Modifier;

import javafx.beans.property.StringProperty;

public class Race extends ModifyingObject{
    
    public Race(String raceName) {
        this.name.set(raceName);
    }
    
    public StringProperty getRaceNameProperty() {
        return name;
    }

}
