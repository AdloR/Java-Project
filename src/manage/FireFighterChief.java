package manage;

import simu.Simulateur;

/**
 * The mother class of all the different strategies that will be implemented.
 */
public abstract class FireFighterChief {

    /**
     * The function that will implement the chosen strategy.
     *
     * @param sim the Simulateur.
     */
    public abstract void affectRobot(Simulateur sim);

}
