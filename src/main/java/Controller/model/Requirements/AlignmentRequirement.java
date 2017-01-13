package Controller.model.Requirements;

import Controller.model.Character;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

public class AlignmentRequirement implements Requirement {
    @Override
    public boolean validate(Character mainChar, JSONObject requirement) {
        JSONArray validAlignments = requirement.getJSONArray("alignments");
        return validAlignments.contains(mainChar.alignment);
    }
}
