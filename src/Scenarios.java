import java.awt.Color;

import gui.GUISimulator;
import io.LecteurDonnees;
import robot.Robot;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.Evenement;
import simu.evenements.InterventionEven;
import simu.evenements.RemplissageEven;
import simu.evenements.mouvements.RobotBougeDirEven;
import terrain.Carte;
import terrain.Direction;

class Scenario0 {
    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Carte carte = donnees.getCarte();
        int width = carte.getNbColonnes() * Simulateur.LARGEUR_TILES;
        int height = carte.getNbLignes() * Simulateur.LARGEUR_TILES;
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);

        Evenement e1 = new RobotBougeDirEven(1, donnees.getRobots().get(0), Direction.NORD);
        Evenement e2 = new RobotBougeDirEven(2, donnees.getRobots().get(0), Direction.NORD);
        Evenement e3 = new RobotBougeDirEven(3, donnees.getRobots().get(0), Direction.NORD);
        Evenement e4 = new RobotBougeDirEven(4, donnees.getRobots().get(0), Direction.NORD);

        Simulateur sim = new Simulateur(gui, donnees);
        sim.ajouteEvenement(e1);
        sim.ajouteEvenement(e2);
        sim.ajouteEvenement(e3);
        sim.ajouteEvenement(e4);
    }
}

class Scenario1 {
    public static void main(String[] args) {

        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Carte carte = donnees.getCarte();
        int width = carte.getNbColonnes() * Simulateur.LARGEUR_TILES;
        int height = carte.getNbLignes() * Simulateur.LARGEUR_TILES;
        GUISimulator gui = new GUISimulator(width, height, Color.BLACK);

        Robot r = donnees.getRobots().get(1);
        Simulateur sim = new Simulateur(gui, donnees);

        sim.ajouteEvenement(new RobotBougeDirEven(1, r, Direction.NORD));
        sim.ajouteEvenement(new InterventionEven(2, r, sim)); // Jusque 8
        sim.ajouteEvenement(new RobotBougeDirEven(8, r, Direction.OUEST));
        sim.ajouteEvenement(new RobotBougeDirEven(10, r, Direction.OUEST));
        sim.ajouteEvenement(new RemplissageEven(11, r, sim));
        sim.ajouteEvenement(new RobotBougeDirEven(612, r, Direction.EST));
        sim.ajouteEvenement(new RobotBougeDirEven(614, r, Direction.EST));
        sim.ajouteEvenement(new InterventionEven(615, r, sim));

        sim.draw();
    }
}
