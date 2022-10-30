package simu;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import exceptions.ForbiddenMoveException;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import gui.Text;
import io.LecteurDonnees;
import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPattes;
import robot.RobotARoues;
import simu.evenements.Evenement;
import terrain.Case;

public class Simulateur implements Simulable {
    public static final int LARGEUR_TILES = 64;
    GUISimulator gui;

    private long dateSimulation;
    private DonneesSimulation donnees;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>();
    private List<Evenement> history = new LinkedList<>();

    public Simulateur(GUISimulator gui, DonneesSimulation donnees) {
        this.gui = gui;
        this.donnees = donnees;
        gui.setSimulable(this);
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
        while (evenements.peek() != null && evenements.peek().getDate() == dateSimulation) {
            Evenement e = evenements.poll();
            try {
                e.execute();
            } catch (ForbiddenMoveException exception) {
            }
            history.add(e);
        }
        draw();
    }

    @Override
    public void restart() {
        evenements.addAll(history);
        history = new LinkedList<>();
        dateSimulation = 0;
        try {
            donnees = LecteurDonnees.lire(donnees.getFichierDonnees());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
    }

    public void draw() {
        gui.reset();
        for (Case c : donnees.getCarte().getCases()) {
            int x = c.getColonne() * LARGEUR_TILES;
            int y = c.getLigne() * LARGEUR_TILES;
            switch (c.getType()) {
                case EAU:
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/eau.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                    break;
                case FORET:
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/foret.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                    break;
                case ROCHE:
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/roche.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                    break;
                case TERRAIN_LIBRE:
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/libre.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                    break;
                case HABITAT:
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/habitat.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                    break;
                default:
                    gui.addGraphicalElement(new Text(x, y, Color.RED, "E"));
            }
        }

        for (Incendie i : donnees.getIncendies()) {
            if (i.getNbL() <= 0)
                continue;
            int x = i.getFireCase().getColonne() * LARGEUR_TILES;
            int y = i.getFireCase().getLigne() * LARGEUR_TILES;
            gui.addGraphicalElement(
                    new ImageElement(x, y, "assets/feu.png", LARGEUR_TILES, LARGEUR_TILES, gui));
        }

        for (Robot r : donnees.getRobots()) {
            int x = r.getPosition().getColonne() * LARGEUR_TILES;
            int y = r.getPosition().getLigne() * LARGEUR_TILES;
            if (r instanceof Drone)
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/drone.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            else if (r instanceof RobotARoues)
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/roues.png", LARGEUR_TILES, LARGEUR_TILES, gui));
                        else if (r instanceof RobotAChenille)
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/chenille.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            else if (r instanceof RobotAPattes)
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/pattes.png", LARGEUR_TILES, LARGEUR_TILES, gui));

            else
                gui.addGraphicalElement(new Text(x, y, Color.CYAN, "R"));
        }
    }
}
