package Controller.model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

public class Experience {
    public static final long[] XP_TABLE = {0, 2000, 5000, 9000, 15000, 23000, 35000, 51000, 75000, 105000, 155000, 220000, 315000, 445000, 635000, 890000, 1300000, 1800000, 2550000, 3600000};
    
    private static final LongProperty nextLevelXpValue = new SimpleLongProperty(XP_TABLE[1]);
    
    public static LongProperty getNextLevelXpValueProperty() {
        return nextLevelXpValue;
    }
    
    public static void setNextLevelXpValue(int index){
        if(index < 20) {
            nextLevelXpValue.set(XP_TABLE[index]);
        } else {
            nextLevelXpValue.set(getNextLevelXpValueProperty().get() * 2);
        }
    }
}
