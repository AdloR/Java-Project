package robot;

import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;
import terrain.Case;

public abstract class Robot {
    private GUISimulator gui;

    private Case position;
    private int speed;
    private int reservoirMax;
    private int reservoir;
    private int timeRefill;
    private int volumeIntervention;
    private int timeIntervention;

    public Robot(GUISimulator gui) {
        this.gui = gui;
    }

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

    public void draw() {
        int tailleCases = getPosition().getCarte().getTailleCases();
        int x = getPosition().getColonne();
        int y = getPosition().getLigne();
        gui.addGraphicalElement(new Rectangle(x*tailleCases, y*tailleCases, Color.CYAN, Color.CYAN, tailleCases));
    }
}
