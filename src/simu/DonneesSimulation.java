package simu;

import java.util.List;

import robot.Robot;
import terrain.Carte;

public class DonneesSimulation {

    /**
     * @context principal class linking all problem's data.
     * @param incendie the localisation of fire cases.
     * @param carte the map of the interface.
     * @param robot the robots in charge of extincting the fire.
     */
    private List<Incendie> incendies;
    private Carte carte;
    private List<Robot> robots;

    public DonneesSimulation(Carte carte, List<Incendie> incendies, List<Robot> robots) {
        this.incendies = incendies;
        this.carte = carte;
        this.robots = robots;
    }

    public List<Incendie> getIncendies() {
        return incendies;
    }

    public Carte getCarte() {
        return carte;
    }

    public List<Robot> getRobots() {
        return robots;
    }


}
