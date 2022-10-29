package simu;

import java.util.PriorityQueue;

import gui.GUISimulator;
import gui.Simulable;
import simu.evenements.Evenement;

public class Simulateur implements Simulable {
    GUISimulator gui;

    private long dateSimulation;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>();

    public Simulateur(GUISimulator gui) {
        this.gui = gui;
    }

    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
    }

    public void incrementeDate() {
        dateSimulation++;
    }

    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    @Override
    public void next() {
        incrementeDate();
        while (evenements.peek().getDate() == dateSimulation) {
            evenements.poll().execute();
        }
        draw();
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub

    }

    public void draw() {

    }
}
