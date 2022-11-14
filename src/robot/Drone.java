package robot;

import terrain.Case;
import terrain.NatureTerrain;

public class Drone extends Robot {
    public Drone(Case position) {
        this.position = position;
        this.speed = 100 * 1000 / 3600;
        this.reservoirMax = 10000;
        this.reservoir = reservoirMax;
        this.timeRefill = 30 * 60;
        this.volumeIntervention = 10000;
        this.timeIntervention = 30;
    }

    @Override
    public boolean findWater(Case position) {
        return position.getType() == NatureTerrain.EAU;
    }

    @Override
    public void setSpeed(int speed) {
        assert (speed <= 150);
        super.setSpeed(speed * 1000 / 3600);
    }

    @Override
    public boolean isAccessible(Case position) {
        return true;
    }
}