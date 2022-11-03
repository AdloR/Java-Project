import io.LecteurDonnees;
import manage.ElementaryFirefighterChief;
import manage.FireFighterChief;
import simu.DonneesSimulation;
import simu.Simulateur;
import simu.evenements.LancementStrategie;

class StartegieElementaire {

    public static void main(String[] args) {
        DonneesSimulation donnees;
        try {
            donnees = LecteurDonnees.lire("cartes/carteSujet.map");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Simulateur sim = new Simulateur(donnees);

        FireFighterChief elem = new ElementaryFirefighterChief(donnees.getRobots(), donnees.getIncendies(), donnees.getCarte());
        sim.ajouteEvenement(new LancementStrategie(1, sim, elem, false));

        sim.draw();
    }

}
