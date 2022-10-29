package robot;

import terrain.Case;
import terrain.NatureTerrain;

import static terrain.NatureTerrain.HABITAT;
import static terrain.NatureTerrain.TERRAIN_LIBRE;

public class RobotARoues extends Robot {


    public RobotARoues(Case position) {
        this.position = position;
        this.speed = 80;
        this.reservoirMax = 5000;
        this.reservoir = 0;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 5;
    }

    @Override
    public void setPosition(Case position) {
        if (position.getType() == TERRAIN_LIBRE || position.getType() == HABITAT) {
            this.position = position;
        }
    }
}
