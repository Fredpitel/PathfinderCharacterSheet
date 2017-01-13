package Controller.model.Requirements;

public class RequirementFactory {
    public Requirement createRequirement(String type) {
        switch(type){
            case("alignment_requirement"):
                return new AlignmentRequirement();
            case("cleric_requirement"):
                return new ClericRequirement();
            default:
                return null;
        }
    }
}