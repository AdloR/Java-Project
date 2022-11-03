package manage;

import java.util.List;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

public abstract class FireFighterChief {
    protected List<Robot> robots;
    protected List<Incendie> incendies;
    protected int robotsSize;
    protected Carte carte;

    public FireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        this.robots = robots;
        this.incendies = incendies;
        this.carte = carte;
        this.robotsSize = robots.size();
    }

    public abstract void affectRobot(Simulateur sim) throws UnreachableCaseException, NotNeighboringCasesException;

}
