package simu;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import exceptions.ForbiddenMoveException;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import io.LecteurDonnees;
import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPattes;
import robot.RobotARoues;
import simu.evenements.Evenement;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;
import terrain.NatureTerrain;

public class Simulateur implements Simulable {
    public static final int LARGEUR_TILES = 64;
    GUISimulator gui;

    private long dateSimulation;
    private DonneesSimulation donnees;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>();
    private List<Evenement> history = new LinkedList<>();

    public long getDateSimulation() {
        return dateSimulation;
    }

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
        Carte carte = donnees.getCarte();
        for (Case c : carte.getCases()) {
            int x = c.getColonne() * LARGEUR_TILES;
            int y = c.getLigne() * LARGEUR_TILES;
            switch (c.getType()) {
                case EAU:
                    gui.addGraphicalElement(
                            new Rectangle(x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2, new Color(0, 0, 0, 0),
                                    Color.decode("#1a4fc1"),
                                    LARGEUR_TILES));

                    // Doing borders by checking north / south and then west/east for corners
                    // If there is no border north, then check if there is a border west for upper
                    // left part of image
                    if (carte.voisinExiste(c, Direction.NORD)
                            && carte.getVoisin(c, Direction.NORD).getType() != NatureTerrain.EAU) { // There is a border
                                                                                                    // north
                        if (carte.voisinExiste(c, Direction.OUEST)
                                && carte.getVoisin(c, Direction.OUEST).getType() != NatureTerrain.EAU) // And a border
                                                                                                       // west -> corner

                            gui.addGraphicalElement(
                                    new ImageElement(x, y, "assets/eau-no.png", LARGEUR_TILES / 2, LARGEUR_TILES / 2,
                                            gui));
                        else // But no border west -> no corner
                            gui.addGraphicalElement(
                                    new ImageElement(x, y, "assets/eau-nord.png", LARGEUR_TILES / 2, LARGEUR_TILES / 2,
                                            gui));

                        if (carte.voisinExiste(c, Direction.EST)
                                && carte.getVoisin(c, Direction.EST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y, "assets/eau-ne.png", LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                        else
                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y, "assets/eau-nord.png", LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                    } else {
                        if (carte.voisinExiste(c, Direction.OUEST)
                                && carte.getVoisin(c, Direction.OUEST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x, y, "assets/eau-ouest.png", LARGEUR_TILES / 2, LARGEUR_TILES / 2,
                                            gui));

                        if (carte.voisinExiste(c, Direction.EST)
                                && carte.getVoisin(c, Direction.EST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y, "assets/eau-est.png", LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                    }

                    // Same, but only for lower part of image
                    if (carte.voisinExiste(c, Direction.SUD)
                            && carte.getVoisin(c, Direction.SUD).getType() != NatureTerrain.EAU) {
                        if (carte.voisinExiste(c, Direction.OUEST)
                                && carte.getVoisin(c, Direction.OUEST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x, y + LARGEUR_TILES / 2, "assets/eau-so.png", LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                        else
                            gui.addGraphicalElement(
                                    new ImageElement(x, y + LARGEUR_TILES / 2, "assets/eau-sud.png", LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));

                        if (carte.voisinExiste(c, Direction.EST)
                                && carte.getVoisin(c, Direction.EST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2, "assets/eau-se.png",
                                            LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                        else
                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2, "assets/eau-sud.png",
                                            LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                    } else {
                        if (carte.voisinExiste(c, Direction.OUEST)
                                && carte.getVoisin(c, Direction.OUEST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x, y + LARGEUR_TILES / 2, "assets/eau-ouest.png",
                                            LARGEUR_TILES / 2, LARGEUR_TILES / 2,
                                            gui));

                        if (carte.voisinExiste(c, Direction.EST)
                                && carte.getVoisin(c, Direction.EST).getType() != NatureTerrain.EAU)

                            gui.addGraphicalElement(
                                    new ImageElement(x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2, "assets/eau-est.png",
                                            LARGEUR_TILES / 2,
                                            LARGEUR_TILES / 2,
                                            gui));
                    }

                    // Check if we have to complete upper left then upper right
                    // We do so when our direct neighbors are water but not the diagonal one
                    if (carte.voisinExiste(c, Direction.OUEST)) {
                        Case o = carte.getVoisin(c, Direction.OUEST);
                        if (o.getType() == NatureTerrain.EAU) {
                            if (carte.voisinExiste(c, Direction.NORD)
                                    && carte.getVoisin(c, Direction.NORD).getType() == NatureTerrain.EAU) {
                                Case no = carte.getVoisin(o, Direction.NORD);
                                if (no.getType() != NatureTerrain.EAU) {
                                    gui.addGraphicalElement(
                                            new ImageElement(x, y,
                                                    "assets/eau-not-no.png",
                                                    LARGEUR_TILES / 2,
                                                    LARGEUR_TILES / 2,
                                                    gui));
                                }
                            }
                            if (carte.voisinExiste(c, Direction.SUD)
                                    && carte.getVoisin(c, Direction.SUD).getType() == NatureTerrain.EAU) {
                                Case so = carte.getVoisin(o, Direction.SUD);
                                if (so.getType() != NatureTerrain.EAU)
                                    gui.addGraphicalElement(
                                            new ImageElement(x, y + LARGEUR_TILES / 2,
                                                    "assets/eau-not-so.png",
                                                    LARGEUR_TILES / 2,
                                                    LARGEUR_TILES / 2,
                                                    gui));
                            }
                        }
                    }

                    if (carte.voisinExiste(c, Direction.EST)) {
                        Case e = carte.getVoisin(c, Direction.EST);
                        if (e.getType() == NatureTerrain.EAU) {
                            if (carte.voisinExiste(c, Direction.NORD)
                                    && carte.getVoisin(c, Direction.NORD).getType() == NatureTerrain.EAU) {
                                Case ne = carte.getVoisin(e, Direction.NORD);
                                if (ne.getType() != NatureTerrain.EAU) {
                                    gui.addGraphicalElement(
                                            new ImageElement(x + LARGEUR_TILES / 2, y,
                                                    "assets/eau-not-ne.png",
                                                    LARGEUR_TILES / 2,
                                                    LARGEUR_TILES / 2,
                                                    gui));
                                }
                            }
                            if (carte.voisinExiste(c, Direction.SUD)
                                    && carte.getVoisin(c, Direction.SUD).getType() == NatureTerrain.EAU) {
                                Case se = carte.getVoisin(e, Direction.SUD);
                                if (se.getType() != NatureTerrain.EAU)
                                    gui.addGraphicalElement(
                                            new ImageElement(x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2,
                                                    "assets/eau-not-se.png",
                                                    LARGEUR_TILES / 2,
                                                    LARGEUR_TILES / 2,
                                                    gui));
                            }
                        }
                    }

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
                    gui.addGraphicalElement(
                            new ImageElement(x, y, "assets/erreur.png", LARGEUR_TILES, LARGEUR_TILES, gui));
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
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/erreur.png", LARGEUR_TILES, LARGEUR_TILES, gui));
        }
    }
}
