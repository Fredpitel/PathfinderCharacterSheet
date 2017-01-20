package Controller.model.FavoredBonus;


public class HitPointBonus extends FavoredBonus{
    @Override
    public String toString() {
        return("+1 Hit Point");
    }
    
    @Override
    public int getType() {
        return 1;
    }
}
