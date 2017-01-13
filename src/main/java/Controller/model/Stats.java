package Controller.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Stats {
    private final IntegerProperty str; 
    private final IntegerProperty strBonus;
    private final IntegerProperty dex;
    private final IntegerProperty dexBonus;
    private final IntegerProperty con;
    private final IntegerProperty conBonus;
    private final IntegerProperty intel;
    private final IntegerProperty intelBonus;
    private final IntegerProperty wis;
    private final IntegerProperty wisBonus;
    private final IntegerProperty cha;
    private final IntegerProperty chaBonus;

    public Stats() {
        str = new SimpleIntegerProperty(10);
        strBonus = new SimpleIntegerProperty(0);
        dex = new SimpleIntegerProperty(10);
        dexBonus = new SimpleIntegerProperty(0);
        con = new SimpleIntegerProperty(10);
        conBonus = new SimpleIntegerProperty(0);
        intel = new SimpleIntegerProperty(10);
        intelBonus = new SimpleIntegerProperty(0);
        wis = new SimpleIntegerProperty(10);
        wisBonus = new SimpleIntegerProperty(0);
        cha = new SimpleIntegerProperty(10);
        chaBonus = new SimpleIntegerProperty(0);
    }
    
    public void add(String stat, int pointsAdded) {
        switch(stat) {
            case("STRENGTH"):
                setStr(getStr() + pointsAdded);
                break;
            case("DEXTERITY"):
                setDex(getDex() + pointsAdded);
                break;
            case("CONSTITUTION"):
                setCon(getCon() + pointsAdded);
                break;
            case("INTELLIGENCE"):
                setInt(getInt() + pointsAdded);
                break;
            case("WISDOM"):
                setWis(getWis() + pointsAdded);
                break;
            case("CHARISMA"):
                setCha(getCha() + pointsAdded);
                break;           
        }
    }
    
    public void remove(String stat) {
        switch(stat) {
            case("STRENGTH"):
                setStr(getStr() - 1);
                break;
            case("DEXTERITY"):
                setDex(getDex() - 1);
                break;
            case("CONSTITUTION"):
                setCon(getCon() - 1);
                break;
            case("INTELLIGENCE"):
                setInt(getInt() - 1);
                break;
            case("WISDOM"):
                setWis(getWis() - 1);
                break;
            case("CHARISMA"):
                setCha(getCha() - 1);
                break;           
        }
    }
    
    private void updateBonus(IntegerProperty stat, IntegerProperty statBonus) {
        statBonus.set((int) Math.ceil((10 - stat.get()) / 2) * -1);
    }

    public int getStr() {
        return str.get();
    }
    
    public void setStr(int str) {
        this.str.set(str);
        updateBonus(this.str, this.strBonus);
    }
    
    public IntegerProperty getStrProperty() {
        return str;
    }
    
    public int getStrBonus() {
        return strBonus.get();
    }
    
    public void setStrBonus(int bonus) {
        this.strBonus.set(bonus);
    }

    public IntegerProperty getStrBonusProperty() {
        return strBonus;
    }

    public int getDex() {
        return dex.get();
    }
    
    public void setDex(int dex) {
        this.dex.set(dex);
        updateBonus(this.dex, this.dexBonus);
    }
    
    public IntegerProperty getDexProperty() {
        return dex;
    }

    public int getDexBonus() {
        return dexBonus.get();
    }
    
    public void setDexBonus(int bonus) {
        this.dexBonus.set(bonus);
    }
    
    public IntegerProperty getDexBonusProperty() {
        return dexBonus;
    }
    
    public int getCon() {
        return con.get();
    }
    
    public void setCon(int con) {
        this.con.set(con);
        updateBonus(this.con, this.conBonus);
    }

    public IntegerProperty getConProperty() {
        return con;
    }
    
    public int getConBonus() {
        return conBonus.get();
    }
    
    public void setConBonus(int bonus) {
        this.conBonus.set(bonus);
    }

    public IntegerProperty getConBonusProperty() {
        return conBonus;
    }

    public int getInt() {
        return intel.get();
    }
    
    public void setInt(int intel) {
        this.intel.set(intel);
        updateBonus(this.intel, this.intelBonus);
    }
    
    public IntegerProperty getIntelProperty() {
        return intel;
    }
    
    public int getIntBonus() {
        return intelBonus.get();
    }
    
    public void setIntBonus(int bonus) {
        this.intelBonus.set(bonus);
    }

    public IntegerProperty getIntelBonusProperty() {
        return intelBonus;
    }
    
    public int getWis() {
        return wis.get();
    }
    
    public void setWis(int wis) {
        this.wis.set(wis);
        updateBonus(this.wis, this.wisBonus);
    }
    
    public IntegerProperty getWisProperty() {
        return wis;
    }
    
    public int getWisBonus() {
        return wisBonus.get();
    }
    
    public void setWisBonus(int bonus) {
        this.wisBonus.set(bonus);
    }

    public IntegerProperty getWisBonusProperty() {
        return wisBonus;
    }

    public int getCha() {
        return cha.get();
    }
    
    public void setCha(int cha) {
        this.cha.set(cha);
        updateBonus(this.cha, this.chaBonus);
    }
    
    public IntegerProperty getChaProperty() {
        return cha;
    }
    
    public int getChaBonus() {
        return chaBonus.get();
    }
    
    public void setChaBonus(int bonus) {
        this.chaBonus.set(bonus);
    }

    public IntegerProperty getCharBonusProperty() {
        return chaBonus;
    }
    
    
    
    
    
    
}