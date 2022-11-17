
import io.LecteurDonnees;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.Evenement;
import simu.evenements.robot_evenements.DebInterventionEven;
import simu.evenements.robot_evenements.DebRemplissageEven;
import simu.evenements.robot_evenements.mouvements.DebRobotBougeEven;
import terrain.Direction;

class Scenario0 {
    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            if (args.length != 0)
                donnees = LecteurDonnees.lire(args[0]);
            else
                donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        Evenement e1 = new DebRobotBougeEven(1, sim, 0, Direction.NORD);
        Evenement e2 = new DebRobotBougeEven(451, sim, 0, Direction.NORD);
        Evenement e3 = new DebRobotBougeEven(901, sim, 0, Direction.NORD);
        Evenement e4 = new DebRobotBougeEven(1351, sim, 0, Direction.NORD);

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
            if (args.length != 0)
                donnees = LecteurDonnees.lire(args[0]);
            else
                donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        sim.ajouteEvenement(new DebRobotBougeEven(1, sim, 1, Direction.NORD));
        sim.ajouteEvenement(new DebInterventionEven(456, sim, 1)); // Jusque 8
        sim.ajouteEvenement(new DebRobotBougeEven(755, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new DebRobotBougeEven(1209, sim, 1, Direction.OUEST));
        sim.ajouteEvenement(new DebRemplissageEven(2371, sim, 1));
        sim.ajouteEvenement(new DebRobotBougeEven(2971, sim, 1, Direction.EST));
        sim.ajouteEvenement(new DebRobotBougeEven(3425, sim, 1, Direction.EST));
        sim.ajouteEvenement(new DebInterventionEven(4000, sim, 1));

        sim.draw();
    }
}
