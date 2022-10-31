import java.awt.Color;

import gui.GUISimulator;
import io.LecteurDonnees;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.DebInterventionEven;
import simu.evenements.DebRemplissageEven;
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

        Simulateur sim = new Simulateur(donnees);

        Evenement e1 = new RobotBougeDirEven(1, sim, 0, Direction.NORD);
        Evenement e2 = new RobotBougeDirEven(2, sim, 0, Direction.NORD);
        Evenement e3 = new RobotBougeDirEven(3, sim, 0, Direction.NORD);
        Evenement e4 = new RobotBougeDirEven(4, sim, 0, Direction.NORD);

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

        Simulateur sim = new Simulateur(donnees);

        sim.ajouteEvenement(new RobotBougeDirEven(1, sim, 1, Direction.NORD));
        sim.ajouteEvenement(new DebInterventionEven(2, sim, 1)); // Jusque 8
        sim.ajouteEvenement(new RobotBougeDirEven(8, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new RobotBougeDirEven(10, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new DebRemplissageEven(11, sim, 1));
        sim.ajouteEvenement(new RobotBougeDirEven(612, sim, 1, Direction.EST));
        sim.ajouteEvenement(new RobotBougeDirEven(614, sim, 1, Direction.EST));
        sim.ajouteEvenement(new DebInterventionEven(615, sim, 1));

        sim.draw();
    }
}

class Inondation {
    public static void main(String[] args) {

        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/testEau.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        sim.draw();
    }
}
