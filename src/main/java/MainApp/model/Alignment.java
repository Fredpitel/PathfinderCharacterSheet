package MainApp.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alignment {
    public enum possibleAlignments {NO_ALIGNMENT, LG,NG, CG, LN, TN, CN, LE, NE, CE};
    public static possibleAlignments[][] alignmentGrid = {{possibleAlignments.LG, possibleAlignments.NG, possibleAlignments.CG},
                                                          {possibleAlignments.LN, possibleAlignments.TN, possibleAlignments.CN},
                                                          {possibleAlignments.LE, possibleAlignments.NE, possibleAlignments.CE}};
    
    private final StringProperty alignmentName;
    public final ObjectProperty<possibleAlignments> alignment;
    
    public Alignment() {
        alignmentName = new SimpleStringProperty("Choose Alignment");
        alignment = new SimpleObjectProperty(possibleAlignments.NO_ALIGNMENT);
    }
    
    public StringProperty getAlignmentNameProperty() {
        return alignmentName;
    }
    
    public ObjectProperty getAlignmentProperty() {
        return alignment;
    }
    
    public void setAlignment(String alignment, String abb) {
        alignmentName.set(alignment);
        this.alignment.set(possibleAlignments.valueOf(abb));
    }
}
