package manage;

import robot.Robot;
import simu.Incendie;
import terrain.Carte;

import java.util.ArrayList;

public abstract class FireFighterChief {
    protected ArrayList<Robot> robots;
    protected ArrayList<Incendie> incendies;
    protected Carte carte;

    public FireFighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies, Carte carte) {
        this.robots = robots;
        this.incendies = incendies;
        this.carte = carte;
    }

}
