package MainApp.model.Modifier;

import java.util.Iterator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public abstract class ModifyingObject {
    public final StringProperty name = new SimpleStringProperty();
    
    public void removeModifiers(ObservableList<Modifier> modifiers) {
        for(Iterator<Modifier> iter = modifiers.iterator(); iter.hasNext();) {
            if(iter.next().source.equals(this)) {
                iter.remove();
            }
        }
    }
    
    @Override
    public boolean equals(Object o) {
        ModifyingObject mo = (ModifyingObject) o;
        return mo.name.equals(this.name);
    }
}
