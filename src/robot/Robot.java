package robot;

import gui.Simulable;
import terrain.Case;

public abstract class Robot implements Simulable{
    private Case position;
    private int speed;
    private int reservoirMax;
    private int	reservoir;
    private int timeRefill;
    private int volumeIntervention;
    private int timeIntervention;

    public Case getPosition() {
        return position;
    }
    public void setPosition(Case position) {
        this.position = position;
    }
    public int getSpeed() {
        return speed;
    }

    public int deverserEau(int vol) {
        return Integer.min(vol, reservoir);
    }

    public void remplirReservoir() {
        reservoir = reservoirMax;
    }
}
