package MainApp.model;

import MainApp.model.Requirements.Requirement;
import MainApp.model.Requirements.RequirementFactory;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CharClass {
    public String className;
    public String abb;
    public int hitDie;
    public ArrayList<Requirement> requirements;
    public Progression attackProg;
    public Progression fortProg;
    public Progression refProg;
    public Progression wilProg;
    public BooleanProperty validClass;
    
    public CharClass(JSONObject jsonClass, Character mainChar) {
        className = jsonClass.getString("className");
        abb = jsonClass.getString("abb");
        hitDie = jsonClass.getInt("hitdie");
        attackProg = new Progression(jsonClass.getString("attackProg"));
        fortProg = new Progression(jsonClass.getString("fortProg"));
        refProg = new Progression(jsonClass.getString("refProg"));
        wilProg = new Progression(jsonClass.getString("wilProg"));
        
        requirements = new ArrayList();
        JSONArray jsonRequirements = jsonClass.getJSONArray("requirements");
        for(int j = 0; j < jsonRequirements.size(); j++) {
            JSONObject req = jsonRequirements.getJSONObject(j);
            requirements.add(new RequirementFactory().createRequirement(req));
        }
        
        validClass = new SimpleBooleanProperty();
        validClass.bind(Bindings.createBooleanBinding(() -> validateRequirements(mainChar), mainChar.getAlignmentProperty()));
    }
    
    private boolean validateRequirements(Character mainChar) {
        boolean res = true;
        for(Requirement req : requirements) {
            res = req.validate(mainChar);
        }
        
        return res;
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
    
    public BooleanProperty getValidClassProperty() {
        return validClass;
    }
}
