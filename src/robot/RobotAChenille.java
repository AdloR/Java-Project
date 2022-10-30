package robot;

import exceptions.ForbiddenMoveException;
import terrain.Case;
import terrain.NatureTerrain;

public class RobotAChenille extends Robot {

    public RobotAChenille(Case position) {
        this.position = position;
        this.speed = 60;
        this.reservoirMax = 5000;
        this.reservoir = 0;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 8;
    }

    @Override
    public int getSpeedOn(Case place) {
        return place.getType() == NatureTerrain.FORET ? super.getSpeedOn(place) / 2 : super.getSpeedOn(place);
    }

    @Override
    public void setSpeed(int speed) {
        assert (speed <= 80);
        super.setSpeed(speed);
    }

    @Override
    public void setPosition(Case position) throws ForbiddenMoveException {
        if (position.getType() == NatureTerrain.EAU || position.getType() == NatureTerrain.ROCHE) {
            throw new ForbiddenMoveException("Trying to reach inappropriate case type");
        }
        super.setPosition(position);
    }
}
