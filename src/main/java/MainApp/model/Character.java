package MainApp.model;

import MainApp.model.ModifiableObject.HitPoints;
import MainApp.model.ModifiableObject.SavingThrow;
import MainApp.model.ModifiableObject.AbilityScore;
import MainApp.model.ModifiableObject.AC;
import MainApp.model.Modifier.Race;
import MainApp.model.Alignment.possibleAlignments;
import MainApp.model.Modifier.Modifier;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.util.Pair;
import net.sf.json.JSONObject;

public class Character {
    private final StringProperty charName;
    private final StringProperty playerName;
    
    private final ObservableMap<CharClass, Integer> classMap;
    private final StringProperty charClassesString;
    private final ObjectProperty<CharClass> favoredClass;
    
    private final ObservableList<Level> charLevels;
    
    private final AbilityScores abilityScores;
    
    private final HitPoints hitPoints;
    
    private final AC ac;
    
    private final SavingThrow fort;
    private final SavingThrow ref;
    private final SavingThrow wil;
    
    public final ObservableList<String> bonusStatPoints;
    
    public final ObservableList<Modifier> modifiers;
    
    private final IntegerProperty remainingLevels;
    private final IntegerProperty bonusStatLeft;
    private final LongProperty totalXp;
    private final IntegerProperty goldTotal;
    
    public possibleAlignments alignment;
    public God god;
    
    private final Race race;
    private final Wealth wealth;
    
    public Character(String charName, String playerName, int startingLevels, int plat, int gold, int silv, int copp) {
        this.charName = new SimpleStringProperty(charName);
        this.playerName = new SimpleStringProperty(playerName);
        
        this.favoredClass = new SimpleObjectProperty();
        this.charLevels = FXCollections.observableArrayList(level -> new Observable[] {level.getHpGainedProperty(),
                                                                                       level.getHitPointBonusProperty()});
        this.remainingLevels = new SimpleIntegerProperty(startingLevels);
        this.totalXp = new SimpleLongProperty(Experience.XP_TABLE[startingLevels - 1]);
        
        this.classMap = FXCollections.observableMap(new TreeMap<CharClass, Integer>((CharClass c1, CharClass c2) -> c1.className.compareTo(c2.className)));
        this.charClassesString = new SimpleStringProperty();
        
        this.bonusStatPoints = FXCollections.observableArrayList(new ArrayList());
        this.bonusStatLeft = new SimpleIntegerProperty(0);
        
        this.wealth = new Wealth(plat, gold, silv, copp);
        this.goldTotal = new SimpleIntegerProperty(wealth.calculateGoldTotal());
        
        this.modifiers = FXCollections.observableArrayList(new ArrayList());
        
        this.alignment = possibleAlignments.NO_ALIGNMENT;
        this.abilityScores = new AbilityScores(modifiers);
        this.hitPoints =  new HitPoints(this);
        
        this.ac = new AC(this);
        
        this.fort = new SavingThrow("fort", abilityScores.getAbilityScore("CONSTITUTION").getScoreModifierProperty(), classMap, modifiers);
        this.ref = new SavingThrow("ref", abilityScores.getAbilityScore("DEXTERITY").getScoreModifierProperty(), classMap, modifiers);
        this.wil = new SavingThrow("wil", abilityScores.getAbilityScore("WISDOM").getScoreModifierProperty(), classMap, modifiers);
        
        this.race = new Race("Choose Race");

        charClassesString.bind(Bindings.createStringBinding(() -> makeCharClassesString(), charLevels, classMap));
        bonusStatLeft.bind(Bindings.size(charLevels).divide(4).subtract(Bindings.size(bonusStatPoints)));
    }
    
    public void addCharLevels(JSONObject jsonClass, int levelsAdded) {
        if(favoredClass.get() == null || charLevels.isEmpty()) {
            setFavoredClass(new CharClass(jsonClass));
        }
        
        for(int i = 0; i < levelsAdded; i++) {
            CharClass charClass = new CharClass(jsonClass);
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
               if(!level.charClass.equals(favoredClass.get())){
                   setFavoredClass(level.charClass);
               }
            }
        }
        
        setRemainingLevels(getRemainingLevels() + 1);
        Experience.setNextLevelXpValue(charLevels.size());
    }

    public void addBonusStatPoints(String stat, int pointsAdded) {
        abilityScores.getAbilityScore(stat).setBaseValue(abilityScores.getAbilityScore(stat).getBaseValue() + pointsAdded);
        for(int i = 0; i < pointsAdded; i++) {
            bonusStatPoints.add(stat);
        }
    }
    
    public void removeBonusStatPoint(String stat) {
        abilityScores.getAbilityScore(stat).setBaseValue(abilityScores.getAbilityScore(stat).getBaseValue() - 1);
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
    
    public StringProperty getRaceNameProperty() {
        return race.getRaceNameProperty();
    }
    
    public void setRace(String raceName, ArrayList<Pair> modifiers) {
        race.removeModifiers(this.modifiers);
        for(Pair<String, Integer> mod : modifiers) {
            this.modifiers.add(new Modifier(mod.getValue(), race, getAbilityScore(mod.getKey()), Modifier.modifierTypes.RACIAL, true));
            race.name.set(raceName);
        }
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
    
    public ObjectProperty getFavoredClassProperty() {
        return favoredClass;
    }
    
    public CharClass getFavoredClass() {
        return favoredClass.get();
    }
    
    public void setFavoredClass(CharClass charClass) {
        favoredClass.set(charClass);
    }
    
    public ObservableList<Level> getCharLevels() {
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
    
    public AbilityScore getAbilityScore(String ability) {
        return abilityScores.getAbilityScore(ability);
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
    
    public HitPoints getHitPoints() {
        return hitPoints;
    }
    
    public IntegerProperty getTotalAcProperty() {
        return ac.getTotalAcProperty();
    }
    
    public IntegerProperty getBaseAcProperty() {
        return ac.getBaseAcProperty();
    }
    
    public IntegerProperty getFortProperty() {
        return fort.getValueProperty();
    }
    
    public IntegerProperty getRefProperty() {
        return ref.getValueProperty();
    }
    
    public IntegerProperty getWilProperty() {
        return wil.getValueProperty();
    }
    
    public int getGoldTotal() {
        return goldTotal.get();
    }
    
    public IntegerProperty getGoldTotalProperty() {
        return goldTotal;
    }
}
