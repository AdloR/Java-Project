package robot;

import terrain.Case;

public class RobotARoues extends Robot {
    public RobotARoues(Case position) {
        this.position = position;
        this.speed = 80;
        this.reservoirMax = 5000;
        this.reservoir = reservoirMax;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 5;
    }

}
