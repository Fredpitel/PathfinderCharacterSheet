package Controller.model.FavoredBonus;


public class HitPointBonus implements FavoredBonus{
    @Override
    public String toString() {
        return("+1 Hit Point");
    }
    
    @Override
    public int getHpBonus() {
        return 1;
    }
}
