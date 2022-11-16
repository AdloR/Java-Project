package robot;

import terrain.Case;
import terrain.NatureTerrain;

/**
 * A flying Robot:
 * - default speed : 100 Km/h can be set to up to 150 Km/h in the .map file.
 * - can move freely on every kind of space with constant speed.
 * - reservoir size : 10000 L
 * - refill : 30 min over a water tile.
 * - intervention : it's whole reservoir in 30s
 */
public class Drone extends Robot {

    /**
     * Drone constructor
     *
     * @param position The tile where the robot spawn.
     */
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