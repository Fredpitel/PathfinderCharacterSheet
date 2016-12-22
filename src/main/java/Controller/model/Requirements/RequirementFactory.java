package Controller.model.Requirements;

public class RequirementFactory {
    public Requirement createRequirement(String type) {
        switch(type){
            case("alignment_requirement"):
                return new AlignmentRequirement();
            default:
                return null;
        }
    }
}