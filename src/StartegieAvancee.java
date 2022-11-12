import io.LecteurDonnees;
import manage.AdvancedFireFighterChief;
import manage.FireFighterChief;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.LancementStrategie;

public class StartegieAvancee {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new AdvancedFireFighterChief(donnees.getRobots(), donnees.getIncendies(), donnees.getCarte());
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }
}
