import io.LecteurDonnees;
import manage.ImprovedFirefighterChief;
import manage.FireFighterChief;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.LancementStrategie;

public class StrategieMieux {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/mushroomOfHell-20x20.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new ImprovedFirefighterChief(donnees.getRobots(), donnees.getIncendies(),
                donnees.getCarte());
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }
}
