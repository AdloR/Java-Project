package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import robot.Drone;
import robot.Robot;
import robot.RobotAChenille;
import robot.RobotAPattes;
import robot.RobotARoues;
import simu.DonneesSimulation;
import simu.Incendie;
import terrain.Carte;
import terrain.Case;
import terrain.NatureTerrain;

/**
 * .map files reader.
 * Data of tiles ({@code Case}), robots and wildfires ({@code Incendie}) are read in given file. It prompts them and loads them in a {@code DonneesSimulation} for the simulator use.
 */
public class LecteurDonnees {

    /**
     * Read and display data from given file. It then returns a {@code DonneesSimulation} object with the data.
     *
     * @param fichierDonnees Name of the .map file to read.
     */
    public static DonneesSimulation lire(String fichierDonnees) throws FileNotFoundException, DataFormatException {
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Carte carte = lecteur.lireCarte();
        ArrayList<Incendie> incendies = lecteur.lireIncendies(carte);
        ArrayList<Robot> robots = lecteur.lireRobots(carte);
        scanner.close();
        System.out.println("\n == Lecture terminee");
        return new DonneesSimulation(carte, incendies, robots, fichierDonnees);
    }

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     *
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
            throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     *
     * @throws DataFormatException Given file is not a correct .map file (see subject).
     */
    private Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt(); // en m
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);
            Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    carte.setCase(lireCase(carte, lig, col));
                }
            }

            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }

    /**
     * Lit et affiche les donnees d'une case.
     */
    private Case lireCase(Carte carte, int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature;
        NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            // NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);
            nature = NatureTerrain.valueOf(chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
        return new Case(carte, lig, col, nature);
    }

    /**
     * Lit et affiche les donnees des incendies.
     */
    private ArrayList<Incendie> lireIncendies(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        ArrayList<Incendie> incendies = new ArrayList<>();
        try {
            int nbuildcendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbuildcendies);
            for (int i = 0; i < nbuildcendies; i++) {
                lireIncendie(i, incendies, carte);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbuildcendies");
        }
        return incendies;
    }

    /**
     * Lit et affiche les donnees du i-eme incendie.
     *
     * @param i Index of read {@code Incendie}.
     */
    private void lireIncendie(int i, ArrayList<Incendie> incendies, Carte carte) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            Incendie incendie = new Incendie(carte.getCase(lig, col), intensite);
            incendies.add(incendie);
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }

    /**
     * Lit et affiche les donnees des robots.
     */
    private ArrayList<Robot> lireRobots(Carte carte) throws DataFormatException {
        ArrayList<Robot> robots = new ArrayList<>();
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                robots.add(lireRobot(carte, i));
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
        return robots;
    }

    /**
     * Lit et affiche les donnees du i-eme robot.
     *
     * @param i Index of read {@code Incendie}.
     */
    private Robot lireRobot(Carte carte, int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();

            /* Create Robot of matching type */
            System.out.print("\t type = " + type);
            Robot robot;
            switch (type) {
                case "DRONE":
                    robot = new Drone(carte.getCase(lig, col));
                    break;
                case "ROUES":
                    robot = new RobotARoues(carte.getCase(lig, col));
                    break;
                case "CHENILLES":
                    robot = new RobotAChenille(carte.getCase(lig, col));
                    break;
                case "PATTES":
                    robot = new RobotAPattes(carte.getCase(lig, col));
                    break;
                default:
                    throw new DataFormatException(
                            "type de robot invalide. Attendu: DRONE | ROUES | CHENILLES | PATTES");
            }

            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)"); // 1 or more digit(s) ?
            // pour lire un flottant: ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
                robot.setSpeed(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();

            return robot;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

    /**
     * Ignore toute (fin de) ligne commencant par '#'
     */
    private void ignorerCommentaires() {
        while (scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     *
     * @throws DataFormatException Given file is not a correct .map file (see subject).
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
