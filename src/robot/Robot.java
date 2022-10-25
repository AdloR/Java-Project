package robot;

import terrain.Case;

public abstract class Robot {
    private Case position;
    private int speed;
    private int reservoir;
    private int timeRefill;
    private int volumeIntervention;
    private int timeIntervention;

    public Case getPosition() {
        return position;
    }
    public void setPosition(Case position) {
        this.position = position;
    }
    public int getSpeed() {
        return speed;
    }

    public void pourWater(int vol) {
        // TO DO
    }

    public void fillWater() {
        //TO DO
    }
}
