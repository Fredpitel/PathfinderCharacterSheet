package Controller.model.FavoredBonus;

import Controller.model.Character;

public interface FavoredBonus {
    public void modifyMainChar(Character mainChar);
    public void unModifyMainChar(Character mainChar);
    public String getBonusString();
}
