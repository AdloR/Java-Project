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
    private Incendie[] incendies;
    private Carte carte;
    private Robot[] robots;

    public DonneesSimulation(Carte carte, Incendie incendie, Robot robot) {
        this.incendie = incendie;
        this.carte = carte;
        this.robot = robot;
    }


}
