package Controller.model;

import net.sf.json.JSONObject;

public class CharClass {
    public String className;
    public String abb;
    public int hitDie;
    public Progression attackProg;
    public Progression fortProg;
    public Progression refProg;
    public Progression wilProg;
    
    public CharClass(JSONObject jsonClass) {
        className = jsonClass.getString("className");
        abb = jsonClass.getString("abb");
        hitDie = jsonClass.getInt("hitdie");
        attackProg = new Progression(jsonClass.getString("attackProg"));
        fortProg = new Progression(jsonClass.getString("fortProg"));
        refProg = new Progression(jsonClass.getString("refProg"));
        wilProg = new Progression(jsonClass.getString("wilProg"));
    }
    
    public CharClass(String className) {
        this.className = className;
    }
    
    public Progression.progSpeed getProg(String name) {
        switch(name){
            case "fort":
                return fortProg.speed;
            case "ref":
                return refProg.speed;
            case "wil":
                return wilProg.speed;
            default:
                return null;
        }
    } 
    
    @Override
    public boolean equals(Object o) {
        CharClass charClass = (CharClass) o;
        return this.className.equals(charClass.className);
    }
}
