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
    

}
