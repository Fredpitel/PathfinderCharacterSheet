package Controller.model.FavoredBonus;

import Controller.model.Character;

public class SkillPointBonus implements FavoredBonus {
    public String bonusName;
    
    @Override
    public void modifyMainChar(Character mainChar) {
        mainChar.setMaxSkillPoints(mainChar.getMaxSkillPoints()+ 1);
    }
    
    @Override
    public void unModifyMainChar(Character mainChar) {
        mainChar.setMaxSkillPoints(mainChar.getMaxSkillPoints() - 1);
    }
    
    public SkillPointBonus() {
        this.bonusName = "SkillPoint";
    }
    
    @Override
    public String getBonusString() {
        return bonusName;
    }
}