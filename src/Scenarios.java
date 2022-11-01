
import io.LecteurDonnees;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.DebInterventionEven;
import simu.evenements.DebRemplissageEven;
import simu.evenements.Evenement;
import simu.evenements.mouvements.DebRobotBougeDirEven;
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

        Evenement e1 = new DebRobotBougeDirEven(1, sim, 0, Direction.NORD);
        Evenement e2 = new DebRobotBougeDirEven(451, sim, 0, Direction.NORD);
        Evenement e3 = new DebRobotBougeDirEven(901, sim, 0, Direction.NORD);
        Evenement e4 = new DebRobotBougeDirEven(1351, sim, 0, Direction.NORD);

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

        sim.ajouteEvenement(new DebRobotBougeDirEven(1, sim, 1, Direction.NORD));
        sim.ajouteEvenement(new DebInterventionEven(453, sim, 1)); // Jusque 8
        sim.ajouteEvenement(new DebRobotBougeDirEven(459, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new DebRobotBougeDirEven(911, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new DebRemplissageEven(1362, sim, 1));
        sim.ajouteEvenement(new DebRobotBougeDirEven(1963, sim, 1, Direction.EST));
        sim.ajouteEvenement(new DebRobotBougeDirEven(1965, sim, 1, Direction.EST));
        sim.ajouteEvenement(new DebInterventionEven(1966, sim, 1));

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
