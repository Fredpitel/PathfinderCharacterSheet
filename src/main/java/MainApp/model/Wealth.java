package MainApp.model;


public class Wealth {
    private int plat;
    private int gold;
    private int silv;
    private int copp;
    
    public Wealth(int plat, int gold, int silv, int copp) {
        this.plat = plat;
        this.gold = gold;
        this.silv = silv;
        this.copp = copp;
    }
    
    public void getMoney() {
        
    }
    
    public int calculateGoldTotal() {
        return (plat * 10) + gold;
    }
}
