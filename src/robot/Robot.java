package robot;

import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;
import terrain.Case;

public abstract class Robot {
    private Case position;
    private int speed;
    private int reservoirMax;
    private int reservoir;
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
