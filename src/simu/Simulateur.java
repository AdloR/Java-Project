package simu;

import java.util.LinkedList;
import java.util.Queue;

import simu.evenements.Evenement;

public class Simulateur {
    private long dateSimulation;
    private Queue<Evenement> evenements = new LinkedList<>();

    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
    }

    public void incrementeDate() {
        dateSimulation++;
    }

    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }
}
