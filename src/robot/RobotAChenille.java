package robot;

import terrain.Case;
import terrain.NatureTerrain;

public class RobotAChenille extends Robot {

    public RobotAChenille(Case position) {
        this.position = position;
        this.speed = 60;
        this.reservoirMax = 5000;
        this.reservoir = reservoirMax;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 8;
    }

    @Override
    public int getSpeed() {
        return this.position.getType() == NatureTerrain.FORET ? super.getSpeed() / 2 : super.getSpeed();
    }

    @Override
    public void setSpeed(int speed) {
        assert (speed <= 80);
        super.setSpeed(speed);
    }

    @Override
    public void setPosition(Case position) {
        if (position.getType() == NatureTerrain.EAU) {
            return;
        }
        if (position.getType() == NatureTerrain.ROCHE) {
            return;
        }
        super.setPosition(position);
    }
}
