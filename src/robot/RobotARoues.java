package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

import static terrain.NatureTerrain.*;

public class RobotARoues extends Robot {

    public RobotARoues(Case position) {
        this.position = position;
        this.speed = 80 * 1000 / 3600;
        this.reservoirMax = 5000;
        this.reservoir = reservoirMax;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 5;
    }

    @Override
    protected boolean findWater() {
        for (Case place : this.position.getCarte().getVoisins(position)) {
            if (place.getType() == NatureTerrain.EAU) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        try {
            super.setPosition(position);
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAccessible(Case position) {
        if (position.getType() == HABITAT || position.getType() == TERRAIN_LIBRE) {
            return true;
        }
        return false;
    }
}
