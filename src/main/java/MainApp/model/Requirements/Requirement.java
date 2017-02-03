package MainApp.model.Requirements;

import MainApp.model.Character;

public interface Requirement {
    public boolean validate(Character mainChar);
}
