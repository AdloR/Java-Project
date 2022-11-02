package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

import static terrain.NatureTerrain.EAU;

public class RobotAPattes extends Robot {
    public RobotAPattes(Case position) {
        this.position = position;
        this.speed = 30 * 1000 / 3600;
        this.volumeIntervention = 10;
        this.timeIntervention = 1;
    }

    @Override
    protected boolean findWater() {
        return true;
    }

    @Override
    public int getSpeedOn(Case place) {
        return place.getType() == NatureTerrain.ROCHE ? 10 : super.getSpeedOn(place);
    }

    @Override
    public int deverserEau() {
        return this.volumeIntervention;
    }

    @Override
    public void remplirReservoir() {
    }

    @Override
    public boolean isAccessible(Case position) {
        if (position.getType() == EAU) {
            return false;
        }
        return true;
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        try {
            super.setPosition(position);
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }
}
