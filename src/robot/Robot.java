package robot;

import exceptions.ForbiddenMove;
import terrain.Case;

public abstract class Robot {
    protected Case position;
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) throws ForbiddenMove {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int deverserEau(int vol) {
        int tmpVol = Integer.min(vol, reservoir);
        reservoir -= tmpVol;
        return tmpVol;
    }

    public void remplirReservoir() {
        reservoir = reservoirMax;
    }
}
