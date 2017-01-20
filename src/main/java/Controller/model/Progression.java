package Controller.model;

public class Progression {
    public static enum progSpeed {SLOW, MEDIUM, FAST};
    public progSpeed speed;
    
    public Progression(String speed) {
        this.speed = getProgSpeed(speed);
    }
    
    private progSpeed getProgSpeed(String speed) {
        switch(speed) {
            case "slow":
                return progSpeed.SLOW;
            case "medium":
                return progSpeed.MEDIUM;
            case "fast":
                return progSpeed.FAST;
            default:
                return null;
        }
    }
    
    public int getNewSave(int level) {
        switch(speed) {
            case SLOW:
                return (int) Math.floor(0.34 * level);
            case FAST:
                return (int) Math.floor(2 + (level/2));
            default:
                return 0;
        }
    }
}
