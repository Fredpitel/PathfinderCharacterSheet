package Controller.model.FavoredBonus;

import Controller.model.Character;

public final class HitPointBonus implements FavoredBonus{
    public String bonusName;
    
    @Override
    public void modifyMainChar(Character mainChar) {
        mainChar.setMaxHp(mainChar.getMaxHp() + 1);
        mainChar.setCurrentHp(mainChar.getCurrentHp() + 1);
    }
    
    @Override
    public void unModifyMainChar(Character mainChar) {
        mainChar.setMaxHp(mainChar.getMaxHp() - 1);
        mainChar.setCurrentHp(mainChar.getCurrentHp() - 1);
    }
    
    public HitPointBonus() {
        this.bonusName = "HitPoint";
    }
    
    @Override
    public String getBonusString() {
        return bonusName;
    }
}
