package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

import static terrain.NatureTerrain.EAU;
import static terrain.NatureTerrain.ROCHE;

public class RobotAChenille extends Robot {

    public RobotAChenille(Case position) {
        this.position = position;
        this.speed = 60 * 1000 / 3600;
        this.reservoirMax = 5000;
        this.reservoir = reservoirMax;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 8;
    }

    @Override
    public boolean findWater(Case position) {
        for (Case place : position.getCarte().getVoisins(position)) {
            if (place.getType() == NatureTerrain.EAU) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getSpeedOn(Case place) {
        return place.getType() == NatureTerrain.FORET ? super.getSpeedOn(place) / 2 : super.getSpeedOn(place);
    }

    @Override
    public void setSpeed(int speed) {
        assert (speed <= 80 * 1000 / 3600);
        super.setSpeed(speed);
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == NatureTerrain.EAU || position.getType() == ROCHE) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        super.setPosition(position);
    }

    @Override
    public boolean isAccessible(Case position) {
        return !(position.getType() == EAU) && !(position.getType() == ROCHE);
    }
}
