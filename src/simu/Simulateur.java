package simu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import javax.imageio.ImageIO;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Simulable;
import io.LecteurDonnees;
import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPattes;
import robot.RobotARoues;
import simu.evenements.Evenement;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;
import terrain.NatureTerrain;

public class Simulateur implements Simulable {

    public static final int LARGEUR_TILES = 64;
    private GUISimulator gui;
    private int width, height;
    private String tmpBackgroundPath;

    private long dateSimulation;
    private DonneesSimulation donnees;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>();
    private List<Evenement> history = new LinkedList<>();

    public long getDateSimulation() {
        return dateSimulation;
    }

    public Simulateur(DonneesSimulation donnees) {
        this.donnees = donnees;
        Carte carte = donnees.getCarte();
        width = carte.getNbColonnes() * Simulateur.LARGEUR_TILES;
        height = carte.getNbLignes() * Simulateur.LARGEUR_TILES;
        gui = new GUISimulator(width, height, Color.BLACK, this);

        drawBackground();
        draw();
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

    public DonneesSimulation getDonnees() {
        return donnees;
    }

    @Override
    public void next() {
        incrementeDate();
        if (dateSimulation % 100 == 0)
            System.out.println(this.dateSimulation);
        while (evenements.peek() != null && evenements.peek().getDate() == dateSimulation) {
            Evenement e = evenements.poll();
            e.execute();
            if (!e.isAuto())
                history.add(e);
        }
        draw();
    }

    @Override
    public void restart() {
        try {
            donnees = LecteurDonnees.lire(donnees.getFichierDonnees());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        evenements.addAll(history);
        for (Evenement e : evenements) {
            if (e instanceof RobotEven) {
                ((RobotEven) e).actualiserRobots();
            }
        }

        history = new LinkedList<>();
        dateSimulation = 0;
        draw();
    }

    public void draw() {
        gui.reset();
        gui.addGraphicalElement(new ImageElement(0, 0, tmpBackgroundPath, width, height, gui)); // Background already
                                                                                                // created

        for (Incendie i : donnees.getIncendies()) {
            if (i.getNbL() <= 0) {
                continue;
            }
            int x = i.getFireCase().getColonne() * LARGEUR_TILES;
            int y = i.getFireCase().getLigne() * LARGEUR_TILES;
            gui.addGraphicalElement(
                    new ImageElement(x, y, "assets/feu.png", LARGEUR_TILES, LARGEUR_TILES, gui));
        }

        for (Robot r : donnees.getRobots()) {
            int x = r.getPosition().getColonne() * LARGEUR_TILES;
            int y = r.getPosition().getLigne() * LARGEUR_TILES;
            if (r instanceof Drone) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/drone.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            } else if (r instanceof RobotARoues) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/roues.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            } else if (r instanceof RobotAChenille) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/chenille.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            } else if (r instanceof RobotAPattes) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/pattes.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            } else {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/erreur.png", LARGEUR_TILES, LARGEUR_TILES, gui));
            }
        }
    }

    private void drawBackground() {
        Carte carte = donnees.getCarte();
        HashMap<String, BufferedImage> tiles = loadImages();

        BufferedImage background = new BufferedImage(width, height, BufferedImage.OPAQUE);
        Graphics g = background.getGraphics();

        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);

        Random r = new Random();

        for (Case c : carte.getCases()) {
            int x = c.getColonne() * LARGEUR_TILES;
            int y = c.getLigne() * LARGEUR_TILES;

            switch (c.getType()) {
                case EAU:
                    drawContinued(carte, c, tiles, "eau", NatureTerrain.EAU, g);
                    break;
                case FORET:
                    drawContinued(carte, c, tiles, "foret", NatureTerrain.FORET, g);
                    break;
                case ROCHE:
                    g.drawImage(tiles.get("roche"), x, y, LARGEUR_TILES, LARGEUR_TILES, null);
                    break;
                case TERRAIN_LIBRE:
                    if (r.nextDouble() < 0.35)
                        g.drawImage(tiles.get("libre2"), x, y, LARGEUR_TILES, LARGEUR_TILES, null);
                    else
                        g.drawImage(tiles.get("libre1"), x, y, LARGEUR_TILES, LARGEUR_TILES, null);
                    break;
                case HABITAT:
                    g.drawImage(tiles.get("habitat"), x, y, LARGEUR_TILES, LARGEUR_TILES, null);
                    break;
                default:
                    g.drawImage(tiles.get("erreur"), x, y, LARGEUR_TILES, LARGEUR_TILES, null);
            }
        }

        try {
            File export = File.createTempFile("cartetemp", ".png");
            export.setWritable(true);
            ImageIO.write(background, "png", export);
            this.tmpBackgroundPath = export.getPath();
            export.setReadable(true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void drawContinued(Carte carte, Case c, HashMap<String, BufferedImage> tiles, String prefix,
            NatureTerrain toCheck, Graphics g) {
        int x = c.getColonne() * LARGEUR_TILES;
        int y = c.getLigne() * LARGEUR_TILES;

        // drawing interior everywhere, other images will draw on top
        g.drawImage(tiles.get(prefix), x, y, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
        g.drawImage(tiles.get(prefix), x + LARGEUR_TILES / 2, y, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
        g.drawImage(tiles.get(prefix), x, y + LARGEUR_TILES / 2, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
        g.drawImage(tiles.get(prefix), x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2, LARGEUR_TILES / 2,
                LARGEUR_TILES / 2, null);

        // Doing borders by checking north / south and then west/east for corners
        // If there is no border north, then check if there is a border west for upper
        // left part of image
        if (carte.voisinExiste(c, Direction.NORD)
                && carte.getVoisin(c, Direction.NORD).getType() != toCheck) { // There is a border
            // north
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) // And a border
            // west -> corner
            {
                g.drawImage(tiles.get(prefix + "-no"), x, y, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
            } else // But no border west -> no corner
            {
                g.drawImage(tiles.get(prefix + "-nord"), x, y, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-ne"), x + LARGEUR_TILES / 2, y, LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            } else {
                g.drawImage(tiles.get(prefix + "-nord"), x + LARGEUR_TILES / 2, y, LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }
        } else {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-ouest"), x, y, LARGEUR_TILES / 2, LARGEUR_TILES / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-est"), x + LARGEUR_TILES / 2, y, LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }
        }

        // Same, but only for lower part of image
        if (carte.voisinExiste(c, Direction.SUD)
                && carte.getVoisin(c, Direction.SUD).getType() != toCheck) {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-so"), x, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            } else {
                g.drawImage(tiles.get(prefix + "-sud"), x, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-se"), x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            } else {
                g.drawImage(tiles.get(prefix + "-sud"), x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }
        } else {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-ouest"), x, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(tiles.get(prefix + "-est"), x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2,
                        LARGEUR_TILES / 2, null);
            }
        }

        // Check if we have to complete upper left then upper right
        // We do so when our direct neighbors are water but not the diagonal one
        if (carte.voisinExiste(c, Direction.OUEST)) {
            Case o = carte.getVoisin(c, Direction.OUEST);
            if (o.getType() == toCheck) {
                if (carte.voisinExiste(c, Direction.NORD)
                        && carte.getVoisin(c, Direction.NORD).getType() == toCheck) {
                    Case no = carte.getVoisin(o, Direction.NORD);
                    if (no.getType() != toCheck) {
                        g.drawImage(tiles.get(prefix + "-not-no"), x, y,
                                LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2, null);
                    }
                }
                if (carte.voisinExiste(c, Direction.SUD)
                        && carte.getVoisin(c, Direction.SUD).getType() == toCheck) {
                    Case so = carte.getVoisin(o, Direction.SUD);
                    if (so.getType() != toCheck) {
                        g.drawImage(tiles.get(prefix + "-not-so"), x, y + LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2, null);
                    }
                }
            }
        }

        if (carte.voisinExiste(c, Direction.EST)) {
            Case e = carte.getVoisin(c, Direction.EST);
            if (e.getType() == toCheck) {
                if (carte.voisinExiste(c, Direction.NORD)
                        && carte.getVoisin(c, Direction.NORD).getType() == toCheck) {
                    Case ne = carte.getVoisin(e, Direction.NORD);
                    if (ne.getType() != toCheck) {
                        g.drawImage(tiles.get(prefix + "-not-ne"), x + LARGEUR_TILES / 2, y,
                                LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2, null);
                    }
                }
                if (carte.voisinExiste(c, Direction.SUD)
                        && carte.getVoisin(c, Direction.SUD).getType() == toCheck) {
                    Case se = carte.getVoisin(e, Direction.SUD);
                    if (se.getType() != toCheck) {
                        g.drawImage(tiles.get(prefix + "-not-se"), x + LARGEUR_TILES / 2, y + LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2,
                                LARGEUR_TILES / 2, null);
                    }
                }
            }
        }
    }

    private HashMap<String, BufferedImage> loadImages() {
        HashMap<String, BufferedImage> res = new HashMap<>();
        try {
            res.put("eau-est", ImageIO.read(new File("assets/eau/est.png")));
            res.put("eau-ne", ImageIO.read(new File("assets/eau/ne.png")));
            res.put("eau-no", ImageIO.read(new File("assets/eau/no.png")));
            res.put("eau-nord", ImageIO.read(new File("assets/eau/nord.png")));
            res.put("eau-not-ne", ImageIO.read(new File("assets/eau/not-ne.png")));
            res.put("eau-not-no", ImageIO.read(new File("assets/eau/not-no.png")));
            res.put("eau-not-se", ImageIO.read(new File("assets/eau/not-se.png")));
            res.put("eau-not-so", ImageIO.read(new File("assets/eau/not-so.png")));
            res.put("eau-ouest", ImageIO.read(new File("assets/eau/ouest.png")));
            res.put("eau-se", ImageIO.read(new File("assets/eau/se.png")));
            res.put("eau-so", ImageIO.read(new File("assets/eau/so.png")));
            res.put("eau-sud", ImageIO.read(new File("assets/eau/sud.png")));
            res.put("eau", ImageIO.read(new File("assets/eau/eau.png")));

            res.put("foret-est", ImageIO.read(new File("assets/foret/est.png")));
            res.put("foret-ne", ImageIO.read(new File("assets/foret/ne.png")));
            res.put("foret-no", ImageIO.read(new File("assets/foret/no.png")));
            res.put("foret-nord", ImageIO.read(new File("assets/foret/nord.png")));
            res.put("foret-not-ne", ImageIO.read(new File("assets/foret/not-ne.png")));
            res.put("foret-not-no", ImageIO.read(new File("assets/foret/not-no.png")));
            res.put("foret-not-se", ImageIO.read(new File("assets/foret/not-se.png")));
            res.put("foret-not-so", ImageIO.read(new File("assets/foret/not-so.png")));
            res.put("foret-ouest", ImageIO.read(new File("assets/foret/ouest.png")));
            res.put("foret-se", ImageIO.read(new File("assets/foret/se.png")));
            res.put("foret-so", ImageIO.read(new File("assets/foret/so.png")));
            res.put("foret-sud", ImageIO.read(new File("assets/foret/sud.png")));
            res.put("foret", ImageIO.read(new File("assets/foret/foret.png")));

            res.put("roche", ImageIO.read(new File("assets/roche.png")));
            res.put("libre1", ImageIO.read(new File("assets/libre1.png")));
            res.put("libre2", ImageIO.read(new File("assets/libre2.png")));
            res.put("habitat", ImageIO.read(new File("assets/habitat.png")));
            res.put("erreur", ImageIO.read(new File("assets/erreur.png")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("This Error should not have happend. Sources are missing at assets");
        }
        return res;
    }
}
