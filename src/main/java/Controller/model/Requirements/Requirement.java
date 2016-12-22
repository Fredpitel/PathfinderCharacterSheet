package Controller.model.Requirements;

import Controller.model.Character;
import net.sf.json.JSONObject;

public interface Requirement {
    public boolean validate(Character mainChar, JSONObject requirement);
}
