package robot;

import terrain.Case;

public class RobotARoues extends Robot {
    public RobotARoues(Case position, int reservoir, int timeRefill, int volumeIntervention, int timeIntervention) {
        this.position = position;
        this.speed = 80;
        this.reservoirMax = 5000;
        this.reservoir = reservoir;
        this.timeRefill = 10 * 60 - reservoir / 20;
        this.volumeIntervention = volumeIntervention;
        this.timeIntervention = timeIntervention;
    }

}
