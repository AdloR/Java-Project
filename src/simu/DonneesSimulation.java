package simu;

import robot.Robot;
import terrain.Carte;

public class DonneesSimulation {

    /**
     * @context principal class linking all problem's data.
     * @param incendie the localisation of fire cases.
     * @param carte the map of the interface.
     * @param robot the robots in charge of extincting the fire.
     */
    private Incendie incendie;
    private Carte carte;
    private Robot robot;

    public DonneesSimulation(Incendie incendie, Carte carte, Robot robot) {
        this.incendie = incendie;
        this.carte = carte;
        this.robot = robot;
    }


}
