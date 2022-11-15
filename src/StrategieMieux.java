import io.LecteurDonnees;
import manage.BetterFireFighterChief;
import manage.FireFighterChief;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.LancementStrategie;

public class StrategieMieux {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new BetterFireFighterChief(donnees.getRobots(), donnees.getIncendies(),
                donnees.getCarte());
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }
}
