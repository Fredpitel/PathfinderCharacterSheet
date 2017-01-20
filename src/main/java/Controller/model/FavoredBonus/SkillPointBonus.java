package Controller.model.FavoredBonus;

public class SkillPointBonus extends FavoredBonus{
    @Override
    public String toString() {
        return("+1 Skill Point");
    }
    
    @Override
    public int getType() {
        return 0;
    }
}
