package MainApp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alignment {
    public enum possibleAlignments {NO_ALIGNMENT, LG,NG, CG, LN, TN, CN, LE, NE, CE};
    public static possibleAlignments[][] alignmentGrid = {{possibleAlignments.LG, possibleAlignments.NG, possibleAlignments.CG},
                                                          {possibleAlignments.LN, possibleAlignments.TN, possibleAlignments.CN},
                                                          {possibleAlignments.LE, possibleAlignments.NE, possibleAlignments.CE}};
    
    private final StringProperty alignmentName;
    public possibleAlignments alignment;
    
    public Alignment() {
        alignmentName = new SimpleStringProperty("Choose Alignment");
        alignment = possibleAlignments.NO_ALIGNMENT;
    }
    
    public StringProperty getAlignmentProperty() {
        return alignmentName;
    }
    
    public void setAlignment(String alignment, String abb) {
        alignmentName.set(alignment);
        this.alignment = possibleAlignments.valueOf(abb);
    }
}
