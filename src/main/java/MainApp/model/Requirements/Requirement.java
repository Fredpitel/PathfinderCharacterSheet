package MainApp.model.Requirements;

import MainApp.model.Character;
import net.sf.json.JSONObject;

public interface Requirement {
    public boolean validate(Character mainChar, JSONObject requirement);
}
