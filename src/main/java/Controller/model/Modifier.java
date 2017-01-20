package Controller.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Modifier {
    public final String target;
    public final int effect;
    private final StringProperty source;
    private final StringProperty type;
    private final BooleanProperty isStacking;
    
    public Modifier(String target, int effect, String source, String type, boolean isStacking) {
        this.target = target;
        this.effect = effect;
        this.source = new SimpleStringProperty(source);
        this.type = new SimpleStringProperty(type);
        this.isStacking = new SimpleBooleanProperty(isStacking);
    }
}
