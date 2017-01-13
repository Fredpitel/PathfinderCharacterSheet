package Controller.model;

import Controller.MainApp;
import Controller.model.Alignment.possibleAlignments;
import Controller.model.FavoredBonus.FavoredBonusFactory;
import java.util.ArrayList;
import java.util.TreeMap;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Character {
    
    private final StringProperty charName;
    private final StringProperty playerName;
    private final StringProperty charClassesString;
    private final IntegerProperty maxHp;
    private final IntegerProperty currentHp;
    private final IntegerProperty maxSkillPoints;
    private final IntegerProperty remainingLevels;
    private final IntegerProperty currentLevel;
    private final IntegerProperty bonusLeft;
    private final LongProperty totalXp;
    private final IntegerProperty goldTotal;
    
    public possibleAlignments alignment;
    public God god;
    public final ArrayList<Level> charLevels;  
    public final ArrayList<String> bonusPoints;
    public String favoredClass;
    public Stats stats;
    
    private final Wealth wealth;
    private final MainApp mainApp;
    
    public Character(MainApp mainApp, String charName, String playerName, int startingLevels, int plat, int gold, int silv, int copp) {
        this.mainApp = mainApp;
        this.charName = new SimpleStringProperty(charName);
        this.playerName = new SimpleStringProperty(playerName);
        this.charClassesString = new SimpleStringProperty("No class");
        this.remainingLevels = new SimpleIntegerProperty(startingLevels);
        this.currentLevel = new SimpleIntegerProperty(0);
        this.totalXp = new SimpleLongProperty(Experience.XP_TABLE[startingLevels - 1]);
        this.bonusLeft = new SimpleIntegerProperty(0);
        this.maxHp = new SimpleIntegerProperty(0);
        this.currentHp = new SimpleIntegerProperty(0);
        this.maxSkillPoints = new SimpleIntegerProperty(0);
        this.wealth = new Wealth(plat, gold, silv, copp);
        this.goldTotal = new SimpleIntegerProperty(wealth.calculateGoldTotal());
        this.alignment = possibleAlignments.NO_ALIGNMENT;
        this.charLevels = new ArrayList();
        this.bonusPoints = new ArrayList();
        this.favoredClass = null;
        this.stats = new Stats();
    }
    
    public void addCharLevels(String className, int levelsAdded, int hitDie) {
        if(charLevels.isEmpty()) {
            favoredClass = className;
        }
        
        for(int i = 0; i < levelsAdded; i++) {
            Level newLevel = new Level(className, favoredClass, hitDie);
            
            setCurrentLevel(getCurrentLevel() + 1);
            if(className.equals(favoredClass)) {
                newLevel.favoredBonus.modifyMainChar(this);
            }
            
            charLevels.add(newLevel);
            
            if(newLevel.levelNumber % 4 == 0) {
                setBonusLeft(getBonusLeft() + 1);
            }
            
            Experience.setNextLevelXpValue(getCurrentLevel());
        }
        
        updateHp();
        setRemainingLevels(getRemainingLevels() - levelsAdded);
        updateClassesString();
    }
    
    public void removeLevel(Level level) {
        charLevels.remove(level);
        Level.levelCounter--;
        if(level.className.equals(favoredClass)) {
            level.favoredBonus.unModifyMainChar(this);
        }
        
        for(int i = 1; i <= charLevels.size(); i++) {
            Level leveltemp = charLevels.get(i - 1);
            leveltemp.levelNumber = i;
            if(i == 1) {
               leveltemp.hpGained = leveltemp.hitDie;
               if(!leveltemp.className.equals(favoredClass)){
                   updateFavoredClass(leveltemp.className);
                   favoredClass = leveltemp.className;
                   leveltemp.favoredBonus = new FavoredBonusFactory().createBonus("+1 Hit Point");
               }
            }
        }
        
        if(getCurrentLevel() % 4 == 0){
            setBonusLeft(getBonusLeft() - 1);
        }
        setCurrentLevel(getCurrentLevel() - 1);
        setRemainingLevels(getRemainingLevels() + 1);
        Experience.setNextLevelXpValue(getCurrentLevel());
        updateHp();
        updateClassesString();
    }
    
    public void updateFavoredClass(String newFavoredClass) {
        for(Level level : charLevels) {
            if(level.favoredBonus != null) {
                level.favoredBonus.unModifyMainChar(this);
            }
            if(level.className.equals(newFavoredClass)) {
                level.favoredBonus = new FavoredBonusFactory().createBonus("+1 Hit Point");
                level.favoredBonus.modifyMainChar(this);
                level.setIsFavoredClass(true);
            } else {
                level.favoredBonus = null;
                level.setIsFavoredClass(false);
            }
        }
        
        favoredClass = newFavoredClass;
    }
    
    private void updateHp() {
        setMaxHp(0);
        setCurrentHp(0);
        for(Level level : charLevels) {
            setMaxHp(getMaxHp() + level.hpGained + stats.getConBonus());
            setCurrentHp(getCurrentHp() + level.hpGained + stats.getConBonus());
            if(level.className.equals(favoredClass)){
                level.favoredBonus.modifyMainChar(this);
            }
        }
    }
    
    private void updateClassesString() {
        TreeMap<String, Integer> classMap = new TreeMap();
        
        for(Level level : charLevels) {
            int count = classMap.containsKey(level.className) ? classMap.get(level.className) : 0;
            classMap.put(level.className, count + 1);
        }
        
        if(classMap.size() == 1) {
            setCharClasses(charLevels.get(0).className + " " + classMap.get(charLevels.get(0).className));
        } else {
            String newClassesString = "";
            for(String key : classMap.keySet()) {
                JSONArray classes = mainApp.jsonClasses.getJSONArray("classes");
                for(int i = 0; i < classes.size(); i++) {
                    JSONObject charClass = classes.getJSONObject(i);
                    if(charClass.getString("className").equals(key)){
                        newClassesString += (charClass.getString("abb") + " " + classMap.get(key) + "/");
                    }
                }
            }
            if(!newClassesString.equals("")) {
                setCharClasses(newClassesString.substring(0, newClassesString.length() - 1));
            } else {
                setCharClasses("No class");
            }
        }
    }

    public void addBonusStatPoints(String stat, int pointsAdded) {
        stats.add(stat, pointsAdded);
        for(int i = 0; i < pointsAdded; i++) {
            bonusPoints.add(stat);
        }
        setBonusLeft(getBonusLeft() - pointsAdded);
        
        if(stat.equals("CONSTITUTION")){
            updateHp();
        }
    }
    
    public void removeBonusStatPoint(String stat) {
        stats.remove(stat);
        bonusPoints.remove(stat);
        setBonusLeft(getBonusLeft() + 1);
        
        if(stat.equals("CONSTITUTION")){
            updateHp();
        }
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
    
    public String getCharClasses() {
        return charClassesString.get();
    }

    public void setCharClasses(String charName) {
        this.charClassesString.set(charName);
    }
    
    public StringProperty charClassesProperty() {
        return charClassesString;
    }
    
    public int getRemainingLevels() {
        return remainingLevels.get();
    }
    
    public void setRemainingLevels(int levels) {
        this.remainingLevels.set(levels);
    }
    
    public IntegerProperty getRemainingLevelsProperty() {
        return remainingLevels;
    }
    
    public int getCurrentLevel() {
        return currentLevel.get();
    }
    
    public void setCurrentLevel(int level) {
        this.currentLevel.set(level);
    }
    
    public IntegerProperty getCurrentLevelProperty() {
        return currentLevel;
    }
    
    public int getBonusLeft() {
        return bonusLeft.get();
    }
    
    public void setBonusLeft(int bonus) {
        this.bonusLeft.set(bonus);
    }
    
    public IntegerProperty getBonusLeftProperty() {
        return bonusLeft;
    }
    
    public long getTotalXp() {
        return totalXp.get();
    }
    
    public void setTotalXp(long xp) {
        this.totalXp.set(xp);
    }
    
    public LongProperty getTotalXpProperty() {
        return totalXp;
    }
    
    public int getMaxHp() {
        return maxHp.get();
    }
    
    public void setMaxHp(int hp) {
        this.maxHp.set(hp);
    }
    
    public IntegerProperty getMaxHpProperty() {
        return maxHp;
    }
    
    public int getCurrentHp() {
        return currentHp.get();
    }
    
    public void setCurrentHp(int hp) {
        this.currentHp.set(hp);
    }
    
    public IntegerProperty getCurrentHpProperty() {
        return currentHp;
    }
    
    public int getMaxSkillPoints() {
        return maxSkillPoints.get();
    }
    
    public void setMaxSkillPoints(int skillPoints) {
        this.maxSkillPoints.set(skillPoints);
    }
    
    public IntegerProperty getMaxSkillPointsProperty() {
        return maxSkillPoints;
    }
    
    public int getGoldTotal() {
        return goldTotal.get();
    }
    
    public IntegerProperty getGoldTotalProperty() {
        return goldTotal;
    }
}
