package manage;

import java.util.List;

import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * The mother class of all the different strategies that will be implemented.
 */
public abstract class FireFighterChief {
    protected List<Robot> robots;
    protected List<Incendie> incendies;
    protected int robotsSize;
    protected Carte carte;

    /**
     * FireFighterChief constructor
     *
     * @param robots List of robots of the map.
     * @param incendies List of {@code Incendie} of the map.
     * @param carte The map ({@code Carte} type).
     */
    public FireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        this.robots = robots;
        this.incendies = incendies;
        this.carte = carte;
        this.robotsSize = robots.size();
    }

    /**
     * The function that will implement the chosen strategy.
     *
     * @param sim the Simulateur.
     */
    public abstract void affectRobot(Simulateur sim);

}
