package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;

import static terrain.NatureTerrain.*;

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

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == EAU || position.getType() == FORET || position.getType() == ROCHE) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        this.position = position;
    }
}
