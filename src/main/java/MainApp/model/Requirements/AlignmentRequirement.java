package MainApp.model.Requirements;

import MainApp.model.Alignment.possibleAlignments;
import MainApp.model.Character;
import java.util.ArrayList;
import net.sf.json.JSONArray;

public class AlignmentRequirement implements Requirement {
   public ArrayList<possibleAlignments> alignments = new ArrayList();
    
    @Override
    public boolean validate(Character mainChar) {
        return alignments.contains((possibleAlignments)mainChar.alignment.getAlignmentProperty().get());
    }
    
    public AlignmentRequirement(JSONArray requirements) {
        for(int i = 0; i < requirements.size(); i++) {
            alignments.add(possibleAlignments.valueOf(requirements.getString(i)));
        }
    }
}
