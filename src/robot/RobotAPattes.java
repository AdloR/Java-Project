package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

public class RobotAPattes extends Robot {
    public RobotAPattes(Case position) {
        this.position = position;
        this.speed = 30;
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
    public int deverserEau(int vol) {
        return vol;
    }

    @Override
    public void remplirReservoir() {
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == NatureTerrain.EAU) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        super.setPosition(position);
    }
}
