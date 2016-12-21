package Controller.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Character {
    private final StringProperty charName;
    private final StringProperty playerName;
    private final IntegerProperty remainingLevels;
    private final IntegerProperty goldTotal;
    
    public Wealth wealth;
    
    public Character(String charName, String playerName, int remainingLevels, int plat, int gold, int silv, int copp) {
        this.charName = new SimpleStringProperty(charName);
        this.playerName = new SimpleStringProperty(playerName);
        this.remainingLevels = new SimpleIntegerProperty(remainingLevels);
        this.wealth = new Wealth(plat, gold, silv, copp);
        this.goldTotal = new SimpleIntegerProperty(wealth.calculateGoldTotal());
    }
    
    public String getCharName() {
        return charName.get();
    }

    public void setCharName(String charName) {
        this.charName.set(charName);
    }
    
    public StringProperty charNameProperty() {
        return charName;
    }
    
    public String getPlayerName() {
        return playerName.get();
    }
    
    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }
    
    public StringProperty playerNameProperty() {
        return playerName;
    }

    public int getRemainingLevels() {
        return remainingLevels.get();
    }
    
    public IntegerProperty remainingLevelsProperty() {
        return remainingLevels;
    }
    
    public int getGoldTotal() {
        return goldTotal.get();
    }
    
    public IntegerProperty getGoldTotalProperty() {
        return goldTotal;
    }
}
