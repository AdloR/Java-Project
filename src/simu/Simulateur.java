package simu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
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

/**
 * Manages the simulation (events handling and display)
 */
public class Simulateur implements Simulable {

    public static int largeur_tuiles = 64;
    private GUISimulator gui;
    private int width, height;
    private String tmpBackgroundPath;

    private long dateSimulation;
    private DonneesSimulation donnees;
    private PriorityQueue<Evenement> evenements = new PriorityQueue<>();
    private Queue<Evenement> history = new LinkedList<>();

    /**
     * The random generator used in background drawing
     */
    private Random r;
    /**
     * Where all the background images are stored.
     * 
     * @see #loadImages()
     */
    private HashMap<String, BufferedImage> tiles;

    /**
     * Simulateur constructor.
     * 
     * @param donnees Simulations data.
     */
    public Simulateur(DonneesSimulation donnees) {
        this.donnees = donnees;
        Carte carte = donnees.getCarte();
        // Choosing the tiles and windows size.
        while ((width = carte.getNbColonnes() * Simulateur.largeur_tuiles) > 1920
                || (height = carte.getNbLignes() * Simulateur.largeur_tuiles) > 1080) {
            largeur_tuiles /= 2;
        }
        gui = new GUISimulator(width, height, Color.BLACK, this);

        gui.setSize(Integer.max(width + 27, 830), height + 110);
        this.r = new Random();

        drawBackground();
        draw();
    }

    /**
     * Date simulation getter. Represent the time of the simulation in seconds.
     * 
     * @return Current time in the simulation.
     */
    public long getDateSimulation() {
        return dateSimulation;
    }

    /**
     * Plan given {@code Evenement}.
     * 
     * @param e Event to happen.
     */
    public void ajouteEvenement(Evenement e) {
        evenements.add(e);
    }

    public void incrementeDate() {
        dateSimulation++;
    }

    /**
     * @return {@code true} if the simulation has ended. {@code false} otherwise.
     */
    public boolean simulationTerminee() {
        return evenements.isEmpty();
    }

    public DonneesSimulation getDonnees() {
        return donnees;
    }

    /**
     * Function called to simulate one step.
     */
    @Override
    public void next() {
        incrementeDate();
        if (dateSimulation % 100 == 0)
            System.out.println(this.dateSimulation);
        while (evenements.peek() != null && evenements.peek().getDate() == dateSimulation) {
            Evenement e = evenements.poll();
            e.execute();
            if (!e.isAuto())
                history.offer(e);
        }
        draw();
    }

    /**
     * Function called to restart the simulation.
     */
    @Override
    public void restart() {
        try {
            donnees = LecteurDonnees.lire(donnees.getFichierDonnees());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getCause());
        }
        evenements.removeIf((e) -> e.isAuto());
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

    /**
     * Draw the background, the robot and the wildfires on the simulation.
     */
    public void draw() {
        gui.reset();
        // The background is drawn and put in a file before, so we only use this file.
        gui.addGraphicalElement(new ImageElement(0, 0, tmpBackgroundPath, width, height, gui));

        for (Incendie i : donnees.getIncendies()) {
            if (i.getNbL() <= 0) {
                continue;
            }
            int x = i.getFireCase().getColonne() * largeur_tuiles;
            int y = i.getFireCase().getLigne() * largeur_tuiles;
            gui.addGraphicalElement(
                    new ImageElement(x, y, "assets/feu.png", largeur_tuiles, largeur_tuiles, gui));
        }

        for (Robot r : donnees.getRobots()) {
            int x = r.getPosition().getColonne() * largeur_tuiles;
            int y = r.getPosition().getLigne() * largeur_tuiles;
            if (r instanceof Drone) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/drone.png", largeur_tuiles, largeur_tuiles, gui));
            } else if (r instanceof RobotARoues) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/roues.png", largeur_tuiles, largeur_tuiles, gui));
            } else if (r instanceof RobotAChenille) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/chenille.png", largeur_tuiles, largeur_tuiles, gui));
            } else if (r instanceof RobotAPattes) {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/pattes.png", largeur_tuiles, largeur_tuiles, gui));
            } else {
                gui.addGraphicalElement(
                        new ImageElement(x, y, "assets/erreur.png", largeur_tuiles, largeur_tuiles, gui));
            }
        }
    }

    /**
     * Draw the background (just the map in this case) of the simulation in a
     * temporary file, which is then used by the normal draw. This is not critical
     * to the project's structure, and was just an easy way to avoid redrawing
     * static tiles at every draw call. It would have been possible (and preferable)
     * to keep this file in memory, and redraw it directly without using a file, but
     * that would have required the use of Java's GUI functions, which were not in
     * the scope of this project.
     * 
     * @see #draw
     */
    private void drawBackground() {
        Carte carte = donnees.getCarte();
        loadImages();

        BufferedImage background = new BufferedImage(width, height, BufferedImage.OPAQUE);
        Graphics g = background.getGraphics();

        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);

        for (Case c : carte.getCases()) {
            int x = c.getColonne() * largeur_tuiles;
            int y = c.getLigne() * largeur_tuiles;

            switch (c.getType()) {
                case EAU:
                    drawContinued(carte, c, tiles, "eau", NatureTerrain.EAU, g);
                    break;
                case FORET:
                    drawContinued(carte, c, tiles, "foret", NatureTerrain.FORET, g, 0.08);
                    break;
                case ROCHE:
                    drawContinued(carte, c, tiles, "roche", NatureTerrain.ROCHE, g, 0.1);
                    break;
                case TERRAIN_LIBRE:
                    g.drawImage(randomImage("libre", 0.35), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
                    g.drawImage(randomImage("libre", 0.35), x + largeur_tuiles / 2, y, largeur_tuiles / 2,
                            largeur_tuiles / 2, null);
                    g.drawImage(randomImage("libre", 0.35), x, y + largeur_tuiles / 2, largeur_tuiles / 2,
                            largeur_tuiles / 2, null);
                    g.drawImage(randomImage("libre", 0.35), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                            largeur_tuiles / 2,
                            largeur_tuiles / 2, null);
                    break;
                case HABITAT:
                    // Some tiles for HABITAT are 2x1, so we first have to decide whether we put a
                    // 1x1 or 2x1 tile.
                    if (r.nextDouble() < 0.8) {
                        g.drawImage(randomImage("habitat", 0.3, 0.1, 0.07, 0.04, 0.025, 0.025, 0.02), x, y,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                        g.drawImage(randomImage("habitat", 0.3, 0.1, 0.07, 0.04, 0.025, 0.025, 0.02),
                                x + largeur_tuiles / 2, y,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                    } else {
                        g.drawImage(randomImage("habitat_2x1_", 0.4), x, y, largeur_tuiles,
                                largeur_tuiles / 2, null);
                    }
                    if (r.nextDouble() < 0.8) {
                        g.drawImage(randomImage("habitat", 0.3, 0.1, 0.07, 0.04, 0.025, 0.025, 0.02), x,
                                y + largeur_tuiles / 2,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                        g.drawImage(randomImage("habitat", 0.3, 0.1, 0.07, 0.04, 0.025, 0.025, 0.02),
                                x + largeur_tuiles / 2,
                                y + largeur_tuiles / 2,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                    } else {
                        g.drawImage(randomImage("habitat_2x1_", 0.4), x, y + largeur_tuiles / 2, largeur_tuiles,
                                largeur_tuiles / 2, null);
                    }
                    break;
                default:
                    g.drawImage(getImage("erreur"), x, y, largeur_tuiles, largeur_tuiles, null);
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

    /**
     * Draw a tile with coherent asset according to neighboring tiles. This is used
     * for the forest and water tiles. There are comments to explain the function,
     * but it is not critical for the project's structure, so don't try to hard to
     * understand it. It draw the images on g, which is just a way to draw various
     * images on the same image for later use.
     * It will choose random images for filled tiles according to chances, used in
     * {@link #randomImage(HashMap, String, double...)}
     * 
     * @param carte   Map of the simulation.
     * @param c       Tile (Case) to draw.
     * @param tiles   Hashmap of buffered images to draw.
     * @param prefix  Beginning of the filenames, might not be equal to c's type.
     * @param toCheck Type of terrain of the neighboring tile that may impact this
     *                drawing.
     * @param g       Graphics to draw the picture in (just know it is necessary to
     *                draw all tiles on the same image)
     * 
     * @see #drawBackground
     * @see #randomImage(HashMap, String, double...)
     */
    private void drawContinued(Carte carte, Case c, HashMap<String, BufferedImage> tiles, String prefix,
            NatureTerrain toCheck, Graphics g, double... chances) {
        int x = c.getColonne() * largeur_tuiles;
        int y = c.getLigne() * largeur_tuiles;

        // drawing interior everywhere, other images will draw on top
        if (chances.length > 0) {
            g.drawImage(randomImage(prefix, chances), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            g.drawImage(randomImage(prefix, chances), x + largeur_tuiles / 2, y, largeur_tuiles / 2,
                    largeur_tuiles / 2, null);
            g.drawImage(randomImage(prefix, chances), x, y + largeur_tuiles / 2, largeur_tuiles / 2,
                    largeur_tuiles / 2, null);
            g.drawImage(randomImage(prefix, chances), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                    largeur_tuiles / 2,
                    largeur_tuiles / 2, null);
        } else {
            g.drawImage(getImage(prefix), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            g.drawImage(getImage(prefix), x + largeur_tuiles / 2, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            g.drawImage(getImage(prefix), x, y + largeur_tuiles / 2, largeur_tuiles / 2, largeur_tuiles / 2, null);
            g.drawImage(getImage(prefix), x + largeur_tuiles / 2, y + largeur_tuiles / 2, largeur_tuiles / 2,
                    largeur_tuiles / 2, null);
        }
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
                g.drawImage(getImage(prefix + "-no"), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            } else // But no border west -> no corner
            {
                g.drawImage(getImage(prefix + "-nord"), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-ne"), x + largeur_tuiles / 2, y, largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            } else {
                g.drawImage(getImage(prefix + "-nord"), x + largeur_tuiles / 2, y, largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            }
        } else {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-ouest"), x, y, largeur_tuiles / 2, largeur_tuiles / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-est"), x + largeur_tuiles / 2, y, largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            }
        }

        // Same, but only for lower part of image
        if (carte.voisinExiste(c, Direction.SUD)
                && carte.getVoisin(c, Direction.SUD).getType() != toCheck) {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-so"), x, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            } else {
                g.drawImage(getImage(prefix + "-sud"), x, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-se"), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            } else {
                g.drawImage(getImage(prefix + "-sud"), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            }
        } else {
            if (carte.voisinExiste(c, Direction.OUEST)
                    && carte.getVoisin(c, Direction.OUEST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-ouest"), x, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
            }

            if (carte.voisinExiste(c, Direction.EST)
                    && carte.getVoisin(c, Direction.EST).getType() != toCheck) {
                g.drawImage(getImage(prefix + "-est"), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                        largeur_tuiles / 2,
                        largeur_tuiles / 2, null);
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
                        g.drawImage(getImage(prefix + "-not-no"), x, y,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                    }
                }
                if (carte.voisinExiste(c, Direction.SUD)
                        && carte.getVoisin(c, Direction.SUD).getType() == toCheck) {
                    Case so = carte.getVoisin(o, Direction.SUD);
                    if (so.getType() != toCheck) {
                        g.drawImage(getImage(prefix + "-not-so"), x, y + largeur_tuiles / 2,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
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
                        g.drawImage(getImage(prefix + "-not-ne"), x + largeur_tuiles / 2, y,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                    }
                }
                if (carte.voisinExiste(c, Direction.SUD)
                        && carte.getVoisin(c, Direction.SUD).getType() == toCheck) {
                    Case se = carte.getVoisin(e, Direction.SUD);
                    if (se.getType() != toCheck) {
                        g.drawImage(getImage(prefix + "-not-se"), x + largeur_tuiles / 2, y + largeur_tuiles / 2,
                                largeur_tuiles / 2,
                                largeur_tuiles / 2, null);
                    }
                }
            }
        }
    }

    /**
     * Chooses a random image acoording to chances.
     * For example, {@code randomImage(..., prefix, chance1)} will choose between
     * two random images with the odds : {@code chance1} for the {@code prefix0}
     * image, and {@code (1-chance1)} for the {@code prefix} image.
     * 
     * @param prefix  The prefic common for all the wanted image names.
     * @param chances The chances for the different one, except for {@code prefix}
     *                which is 1 - (the rest of chances).
     * @return a random {@code BufferedImage} based on the parameters
     */
    private BufferedImage randomImage(String prefix, double... chances) {
        double picked = r.nextDouble();
        double comp = 0;
        String suffix = "";
        for (int i = 0; i < chances.length; i++) {
            if ((comp += chances[i]) > picked) {
                suffix += i;
                break;
            }
        }
        return getImage(prefix + suffix);
    }

    /**
     * Load images from file for drawing, and puts them in a convenient structure
     * for accessing them. This way, we are sure we don't have to reread a file.
     * All images will be put in {@code tiles}
     */
    private void loadImages() {
        tiles = new HashMap<>();
        try {
            tiles.put("eau-est", ImageIO.read(new File("assets/eau/est.png")));
            tiles.put("eau-ne", ImageIO.read(new File("assets/eau/ne.png")));
            tiles.put("eau-no", ImageIO.read(new File("assets/eau/no.png")));
            tiles.put("eau-nord", ImageIO.read(new File("assets/eau/nord.png")));
            tiles.put("eau-not-ne", ImageIO.read(new File("assets/eau/not-ne.png")));
            tiles.put("eau-not-no", ImageIO.read(new File("assets/eau/not-no.png")));
            tiles.put("eau-not-se", ImageIO.read(new File("assets/eau/not-se.png")));
            tiles.put("eau-not-so", ImageIO.read(new File("assets/eau/not-so.png")));
            tiles.put("eau-ouest", ImageIO.read(new File("assets/eau/ouest.png")));
            tiles.put("eau-se", ImageIO.read(new File("assets/eau/se.png")));
            tiles.put("eau-so", ImageIO.read(new File("assets/eau/so.png")));
            tiles.put("eau-sud", ImageIO.read(new File("assets/eau/sud.png")));
            tiles.put("eau", ImageIO.read(new File("assets/eau/eau.png")));

            tiles.put("foret-est", ImageIO.read(new File("assets/foret/est.png")));
            tiles.put("foret-ne", ImageIO.read(new File("assets/foret/ne.png")));
            tiles.put("foret-no", ImageIO.read(new File("assets/foret/no.png")));
            tiles.put("foret-nord", ImageIO.read(new File("assets/foret/nord.png")));
            tiles.put("foret-not-ne", ImageIO.read(new File("assets/foret/not-ne.png")));
            tiles.put("foret-not-no", ImageIO.read(new File("assets/foret/not-no.png")));
            tiles.put("foret-not-se", ImageIO.read(new File("assets/foret/not-se.png")));
            tiles.put("foret-not-so", ImageIO.read(new File("assets/foret/not-so.png")));
            tiles.put("foret-ouest", ImageIO.read(new File("assets/foret/ouest.png")));
            tiles.put("foret-se", ImageIO.read(new File("assets/foret/se.png")));
            tiles.put("foret-so", ImageIO.read(new File("assets/foret/so.png")));
            tiles.put("foret-sud", ImageIO.read(new File("assets/foret/sud.png")));
            tiles.put("foret0", ImageIO.read(new File("assets/foret/foret0.png")));
            tiles.put("foret", ImageIO.read(new File("assets/foret/foret.png")));

            tiles.put("roche-est", ImageIO.read(new File("assets/roche/est.png")));
            tiles.put("roche-ne", ImageIO.read(new File("assets/roche/ne.png")));
            tiles.put("roche-no", ImageIO.read(new File("assets/roche/no.png")));
            tiles.put("roche-nord", ImageIO.read(new File("assets/roche/nord.png")));
            tiles.put("roche-not-ne", ImageIO.read(new File("assets/roche/not-ne.png")));
            tiles.put("roche-not-no", ImageIO.read(new File("assets/roche/not-no.png")));
            tiles.put("roche-not-se", ImageIO.read(new File("assets/roche/not-se.png")));
            tiles.put("roche-not-so", ImageIO.read(new File("assets/roche/not-so.png")));
            tiles.put("roche-ouest", ImageIO.read(new File("assets/roche/ouest.png")));
            tiles.put("roche-se", ImageIO.read(new File("assets/roche/se.png")));
            tiles.put("roche-so", ImageIO.read(new File("assets/roche/so.png")));
            tiles.put("roche-sud", ImageIO.read(new File("assets/roche/sud.png")));
            tiles.put("roche0", ImageIO.read(new File("assets/roche/roche0.png")));
            tiles.put("roche", ImageIO.read(new File("assets/roche/roche.png")));

            tiles.put("libre0", ImageIO.read(new File("assets/libre0.png")));
            tiles.put("libre", ImageIO.read(new File("assets/libre.png")));

            tiles.put("habitat", ImageIO.read(new File("assets/habitat/habitat.png")));
            tiles.put("habitat0", ImageIO.read(new File("assets/habitat/habitat0.png")));
            tiles.put("habitat1", ImageIO.read(new File("assets/habitat/habitat1.png")));
            tiles.put("habitat2", ImageIO.read(new File("assets/habitat/habitat2.png")));
            tiles.put("habitat3", ImageIO.read(new File("assets/habitat/habitat3.png")));
            tiles.put("habitat4", ImageIO.read(new File("assets/habitat/habitat4.png")));
            tiles.put("habitat5", ImageIO.read(new File("assets/habitat/habitat5.png")));
            tiles.put("habitat6", ImageIO.read(new File("assets/habitat/habitat6.png")));
            tiles.put("habitat_2x1_", ImageIO.read(new File("assets/habitat/habitat_2x1_.png")));
            tiles.put("habitat_2x1_0", ImageIO.read(new File("assets/habitat/habitat_2x1_0.png")));

            tiles.put("erreur", ImageIO.read(new File("assets/erreur.png")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("This Error should not have happend. Sources are missing at assets");
        }
    }

    /**
     * Gets the image associated with the name. If no image is found, it will return
     * the error image to be displayed.
     * 
     * @param imageName name of the {@code BufferedImage} to find.
     * 
     * @see #loadImages()
     */
    private BufferedImage getImage(String imageName) {
        return tiles.getOrDefault(imageName, tiles.get("erreur"));
    }
}
