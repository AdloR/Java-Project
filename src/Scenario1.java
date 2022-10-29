import java.awt.Color;

import gui.GUISimulator;
import io.LecteurDonnees;
import robot.DummyRobot;
import robot.Robot;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.Evenement;
import simu.evenements.RobotBougeEven;
import terrain.Carte;

public class Scenario1 {
    public static void main(String[] args) {
        Carte carte = new Carte(10,10);
        GUISimulator gui = new GUISimulator(800, 800, Color.BLACK);

        Robot robot = new DummyRobot();

        Evenement ev = new RobotBougeEven(0, robot, carte.getCase(5, 5));
        DonneesSimulation donnees;
        try{
        donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Simulateur sim = new Simulateur(gui, donnees);
        sim.ajouteEvenement(ev);
        while(!sim.simulationTerminee()) {
            sim.incrementeDate();
        }
    }
}
