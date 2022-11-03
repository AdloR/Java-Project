package manage;

import java.util.List;

import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * @param robots list of robots of the map.
 * @param incendies list of incendie of the map.
 * @param robotsSize size of robots.
 * @param carte the map.
 *
 * The mother class of all the different strategies that will be implemented.
 */
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

    /**
     *
     * @param sim the Simulateur.
     * The functiun that will implement the strategy chosen.
     */
    public abstract void affectRobot(Simulateur sim);

}
