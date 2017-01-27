package Controller.model.FavoredBonus;

public class SkillPointBonus implements FavoredBonus{
    @Override
    public String toString() {
        return("+1 Skill Point");
    }
    
    @Override
    public int getHpBonus() {
        return 0;
    }
}
