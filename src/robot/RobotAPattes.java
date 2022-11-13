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
    public int getReservoir() {
        return 1; // Arbitrary non zero positive value.
    }

    @Override
    protected boolean findWater(Case position) {
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
        return !(position.getType() == EAU);
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == EAU) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        super.setPosition(position);
    }
}
