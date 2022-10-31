package simu;

import java.util.List;

import robot.Robot;
import terrain.Carte;

/**
 * Principal class linking all problem's data.
 */
public class DonneesSimulation {

    private List<Incendie> incendies;
    private Carte carte;
    private List<Robot> robots;
    /**
     * Used to restart the simulation
     */
    private String fichierDonnees;

    /**
     * Constructor
     * @param incendie the localisation of fire cases.
     * @param carte the map of the interface.
     * @param robotIndex the robots in charge of extincting the fire.
     * @param fichierDonnees name of map file, used to restart the simulation.
     */
    public DonneesSimulation(Carte carte, List<Incendie> incendies, List<Robot> robots, String fichierDonnees) {
        this.incendies = incendies;
        this.carte = carte;
        this.robots = robots;
        this.fichierDonnees = fichierDonnees;
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

    public String getFichierDonnees() {
        return fichierDonnees;
    }

}
