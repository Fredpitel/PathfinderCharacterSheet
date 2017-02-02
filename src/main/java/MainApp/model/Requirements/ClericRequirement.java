package MainApp.model.Requirements;

import MainApp.model.Alignment.possibleAlignments;
import MainApp.model.Alignment;
import MainApp.model.Character;
import java.util.ArrayList;
import net.sf.json.JSONObject;

public class ClericRequirement implements Requirement{
    
    @Override
    public boolean validate(Character mainChar, JSONObject requirement){
        if(mainChar.alignment.alignment == possibleAlignments.NO_ALIGNMENT || mainChar.god == null) {
            return true;
        } else {
            possibleAlignments godAlignment = mainChar.god.alignment;
            possibleAlignments charAlignment = mainChar.alignment.alignment;
            
            ArrayList<possibleAlignments> validAlignments = getValidAlignments(godAlignment);
            return validAlignments.contains(charAlignment);
        }
    }
    
    private ArrayList<possibleAlignments> getValidAlignments(possibleAlignments godAlignment) {
        ArrayList<possibleAlignments> validAlignments = new ArrayList();
        validAlignments.add(godAlignment);
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                if(Alignment.alignmentGrid[i][j] == godAlignment) {
                    if(i > 0) {
                        validAlignments.add(Alignment.alignmentGrid[i - 1][j]);
                    }
                    
                    if(i < 2) {
                        validAlignments.add(Alignment.alignmentGrid[i + 1][j]);
                    }
                    
                    if(j > 0) {
                        validAlignments.add(Alignment.alignmentGrid[i][j - 1]);
                    }
                    
                    if(j < 2) {
                        validAlignments.add(Alignment.alignmentGrid[i][j + 1]);
                    }
                    
                    return validAlignments;
                }
            }
        }
        
        return null;
    }
}