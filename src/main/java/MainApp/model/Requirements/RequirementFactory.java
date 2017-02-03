package MainApp.model.Requirements;

import net.sf.json.JSONObject;

public class RequirementFactory {
    public Requirement createRequirement(JSONObject req) {
        switch(req.getString("type")){
            case("alignment_requirement"):
                return new AlignmentRequirement(req.getJSONArray("alignments"));
            case("cleric_requirement"):
                return new ClericRequirement();
            default:
                return null;
        }
    }
}