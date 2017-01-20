package Controller.model;

import Controller.model.Alignment.possibleAlignments;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import net.sf.json.JSONObject;

public class Character {
    private final StringProperty charName;
    private final StringProperty playerName;
    
    private final ObservableMap<CharClass, Integer> classMap;
    private final StringProperty charClassesString;
    private final StringProperty favoredClass;
    
    private final ObservableList<Level> charLevels;
    
    private final ObservableMap<IntegerProperty, ObservableList<Modifier>> modifiers;
    
    private final IntegerProperty maxHp;
    private final IntegerProperty currentHp;
    private final IntegerProperty damageReceived;
    
    private final IntegerProperty baseAC;
    private final IntegerProperty totalAC;
    
    private final IntegerProperty fort;
    private final IntegerProperty ref;
    private final IntegerProperty wil;
    
    private final IntegerProperty bonusHitPoints;
    private final IntegerProperty bonusSkillPoints;
    public final ObservableList<String> bonusStatPoints;
    
    private final IntegerProperty remainingLevels;
    private final IntegerProperty bonusStatLeft;
    private final LongProperty totalXp;
    private final IntegerProperty goldTotal;
    
    public possibleAlignments alignment;
    public God god;
    public Stats stats;
    
    private final Wealth wealth;
    
    public Character(String charName, String playerName, int startingLevels, int plat, int gold, int silv, int copp) {
        this.stats = new Stats();
        
        this.charName = new SimpleStringProperty(charName);
        this.playerName = new SimpleStringProperty(playerName);
        
        this.favoredClass = new SimpleStringProperty();
        this.charLevels = FXCollections.observableArrayList(level -> new Observable[] {level.getHpGainedProperty(),
                                                                                       level.getFavoredBonusProperty(), 
                                                                                       level.charClass.getIsFavoredClassProperty()});
        this.remainingLevels = new SimpleIntegerProperty(startingLevels);
        this.totalXp = new SimpleLongProperty(Experience.XP_TABLE[startingLevels - 1]);
        
        this.classMap = FXCollections.observableMap(new TreeMap<CharClass, Integer>((CharClass c1, CharClass c2) -> c1.className.compareTo(c2.className)));
        this.charClassesString = new SimpleStringProperty();
        this.charClassesString.bind(Bindings.createStringBinding(() -> makeCharClassesString(), charLevels, classMap));
        
        this.modifiers = FXCollections.observableMap(new HashMap<IntegerProperty, ObservableList<Modifier>>());
        
        this.bonusHitPoints = new SimpleIntegerProperty();
        this.bonusHitPoints.bind(Bindings.createIntegerBinding(() -> charLevels.stream().collect(Collectors.summingInt((level) -> level.charClass.getIsFavoredClass() && level.getFavoredBonus().getType() == 1 ? 1 : 0)), charLevels));
        this.bonusSkillPoints = new SimpleIntegerProperty(0);
        this.bonusStatPoints = FXCollections.observableArrayList(new ArrayList());
        this.bonusStatLeft = new SimpleIntegerProperty(0);
        this.bonusStatLeft.bind(Bindings.size(charLevels).divide(4).subtract(Bindings.size(bonusStatPoints)));
        
        this.maxHp = new SimpleIntegerProperty();
        this.maxHp.bind((stats.getStatModifier("CONSTITUTION").multiply(Bindings.size(charLevels)))
                       .add(bonusHitPoints)
                       .add(Bindings.createIntegerBinding(() -> charLevels.stream().collect(Collectors.summingInt(Level::getHpGained)), charLevels))
                       .add(Bindings.createIntegerBinding(() -> modifiers.get(maxHp).stream().collect(Collectors.summingInt((modifier) -> modifier.effect)), modifiers)));
        
        this.currentHp = new SimpleIntegerProperty();
        this.damageReceived = new SimpleIntegerProperty(0);
        this.currentHp.bind(maxHp.subtract(damageReceived));
        
        this.baseAC = new SimpleIntegerProperty(10);
        this.totalAC = new SimpleIntegerProperty();
        this.totalAC.bind(baseAC.add(stats.getStatModifier("CONSTITUTION")));
        
        this.fort = new SimpleIntegerProperty();
        this.fort.bind(stats.getStatModifier("CONSTITUTION")
                      .add(Bindings.createIntegerBinding(() -> classMap.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getKey().fortProg.getNewSave(entry.getValue()))), classMap))
                      .add(Bindings.createIntegerBinding(() -> modifiers.get(fort).stream().collect(Collectors.summingInt((modifier) -> modifier.effect)), modifiers)));
        this.ref = new SimpleIntegerProperty();
        this.ref.bind(stats.getStatModifier("DEXTERITY")
                      .add(Bindings.createIntegerBinding(() -> classMap.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getKey().refProg.getNewSave(entry.getValue()))), classMap))
                      .add(Bindings.createIntegerBinding(() -> modifiers.get(ref).stream().collect(Collectors.summingInt((modifier) -> modifier.effect)), modifiers)));
        this.wil = new SimpleIntegerProperty();
        this.wil.bind(stats.getStatModifier("WISDOM")
                      .add(Bindings.createIntegerBinding(() -> classMap.entrySet().stream().collect(Collectors.summingInt((entry) -> entry.getKey().wilProg.getNewSave(entry.getValue()))), classMap))
                      .add(Bindings.createIntegerBinding(() -> modifiers.get(wil).stream().collect(Collectors.summingInt((modifier) -> modifier.effect)), modifiers)));
        
        this.wealth = new Wealth(plat, gold, silv, copp);
        this.goldTotal = new SimpleIntegerProperty(wealth.calculateGoldTotal());
        this.alignment = possibleAlignments.NO_ALIGNMENT;
    }
    
    public void addCharLevels(JSONObject jsonClass, int levelsAdded) {
        if(charLevels.isEmpty()) {
            setFavoredClass(jsonClass.getString("className"));
        }
        
        for(int i = 0; i < levelsAdded; i++) {
            CharClass charClass = new CharClass(jsonClass, this);
            Level newLevel = new Level(charClass);
            charLevels.add(newLevel);
            Experience.setNextLevelXpValue(charLevels.size());

            int count = classMap.containsKey(charClass) ? classMap.get(charClass) : 0;
            classMap.put(charClass, count + 1);
        }
        
        setRemainingLevels(getRemainingLevels() - levelsAdded);
    }
    
    public void removeLevel(Level removedLevel) {
        Level.levelCounter--;
        
        if(classMap.get(removedLevel.charClass) == 1) {
            classMap.remove(removedLevel.charClass);
        } else {
            classMap.put(removedLevel.charClass, classMap.get(removedLevel.charClass) - 1);
        }
        
        if(charLevels.size() % 4 == 0){
            if(bonusStatPoints.size() == charLevels.size() / 4 && !bonusStatPoints.isEmpty()){
                removeBonusStatPoint(bonusStatPoints.get(bonusStatPoints.size() - 1));
            }
        }
        
        charLevels.remove(removedLevel);
        
        for(int i = 1; i <= charLevels.size(); i++) {
            Level level = charLevels.get(i - 1);
            level.levelNumber = i;
            if(i == 1) {
               level.setHpGained(level.charClass.hitDie);
               if(!level.charClass.className.equals(getFavoredClass())){
                   setFavoredClass(level.charClass.className);
               }
            }
        }
        
        setRemainingLevels(getRemainingLevels() + 1);
        Experience.setNextLevelXpValue(charLevels.size());
    }

    public void addBonusStatPoints(String stat, int pointsAdded) {
        stats.add(stat, pointsAdded);
        for(int i = 0; i < pointsAdded; i++) {
            bonusStatPoints.add(stat);
        }
    }
    
    public void removeBonusStatPoint(String stat) {
        stats.remove(stat);
        bonusStatPoints.remove(stat);
    }
    
    private String makeCharClassesString() {
        String charClasseString = "";
        switch (Bindings.size(classMap).intValue()) {
            case 0:
                charClasseString = "No class";
                break;
            case 1:
                Entry<CharClass, Integer> entry = classMap.entrySet().stream().findFirst().get();
                charClasseString  = entry.getKey().className + " " + entry.getValue();
                break;
            default:
                for(CharClass key : classMap.keySet()) {
                    charClasseString += key.className + " " + classMap.get(key) + "/";
                }   
                charClasseString = charClasseString.substring(0, charClasseString.length() - 1);
                break;
        }

        return charClasseString;
    }
    
    public String getCharName() {
        return charName.get();
    }

    public void setCharName(String charName) {
        this.charName.set(charName);
    }
    
    public StringProperty getCharNameProperty() {
        return charName;
    }
    
    public String getPlayerName() {
        return playerName.get();
    }
    
    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }
    
    public StringProperty getPlayerNameProperty() {
        return playerName;
    }
    
    public String getCharClasses() {
        return charClassesString.get();
    }
    
    public StringProperty getCharClassesProperty() {
        return charClassesString;
    }
    
    public String getFavoredClass() {
        return favoredClass.get();
    }

    public void setFavoredClass(String className) {
        this.favoredClass.set(className);
    }
    
    public StringProperty getFavoredClassProperty() {
        return favoredClass;
    }
    
    public ObservableList getCharLevels() {
        return charLevels;
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
    
    public int getBonusStatLeft() {
        return bonusStatLeft.get();
    }
    
    public void setBonusStatLeft(int bonus) {
        this.bonusStatLeft.set(bonus);
    }
    
    public IntegerProperty getBonusStatLeftProperty() {
        return bonusStatLeft;
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
    
    public int getDamageReceived() {
        return damageReceived.get();
    }
    
    public void setDamageReceived(int damage) {
        this.damageReceived.set(damage);
    }
    
    public IntegerProperty getDamageReceivedProperty() {
        return damageReceived;
    }
    
    public int getBaseAC() {
        return baseAC.get();
    }
    
    public void setBaseAC(int ac) {
        this.baseAC.set(ac);
    }
    
    public IntegerProperty getBaseACProperty() {
        return baseAC;
    }
    
    public int getTotalAC() {
        return totalAC.get();
    }
    
    public void setTotalAC(int ac) {
        this.totalAC.set(ac);
    }
    
    public IntegerProperty getTotalACProperty() {
        return totalAC;
    }
    
    public int getFort() {
        return fort.get();
    }
    
    public void setFort(int fort) {
        this.fort.set(fort);
    }
    
    public IntegerProperty getFortProperty() {
        return fort;
    }
    
    public int getRef() {
        return ref.get();
    }
    
    public void setRef(int ref) {
        this.ref.set(ref);
    }
    
    public IntegerProperty getRefProperty() {
        return ref;
    }
    
    public int getWil() {
        return wil.get();
    }
    
    public void setWil(int wil) {
        this.wil.set(wil);
    }
    
    public IntegerProperty getWilProperty() {
        return wil;
    }
    
    public int getBonusHitPoints() {
        return bonusHitPoints.get();
    }
    
    public void setBonusHitPoints(int bonus) {
        this.bonusHitPoints.set(bonus);
    }
    
    public IntegerProperty getBonusHitPointsProperty() {
        return bonusHitPoints;
    }
    
    public int getGoldTotal() {
        return goldTotal.get();
    }
    
    public IntegerProperty getGoldTotalProperty() {
        return goldTotal;
    }
}
