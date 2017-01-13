package Controller.model.FavoredBonus;

public class FavoredBonusFactory {
    public FavoredBonus createBonus(String bonus) {
        switch(bonus) {
            case "+1 Hit Point":
                return new HitPointBonus();
            case "+1 Skill Point":
                return new SkillPointBonus();
            default:
                return null;
        }
    }
}
