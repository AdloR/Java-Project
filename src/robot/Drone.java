package robot;

import terrain.Case;

public class Drone extends Robot {
    public Drone(Case position)
    {
        this.position = position;
        this.speed = 100;
        this.reservoirMax = 10000;
        this.reservoir = 0;
        this.timeRefill = 30 * 60;
        this.volumeIntervention = 10000;
        this.timeIntervention = 30;
    }

    @Override
    public void setSpeed(int speed)
    {
        assert(speed <= 150);
        super.setSpeed(speed);
    }
}