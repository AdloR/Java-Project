package terrain;

import java.util.ArrayList;
import java.util.Arrays;

import exceptions.NotNeighboringCasesException;

public class Carte {
    private int lignes, colonnes;
    private int tailleCases = 1;
    private Case[] cases;

    public Carte(int nbLignes, int nbColonnes, int tailleCases) {
        lignes = nbLignes;
        colonnes = nbColonnes;
        cases = new Case[lignes * colonnes];
        this.tailleCases = tailleCases;
    }

    /**
     * Gets the number of lines.
     * 
     * @return the number of lines.
     */
    public int getNbLignes() {
        return lignes;
    }

    /**
     * Gets the number of columns.
     * 
     * @return the number of columns
     */
    public int getNbColonnes() {
        return colonnes;
    }

    /**
     * Gets cases' size.
     * 
     * @return cases' size
     */
    public int getTailleCases() {
        return tailleCases;
    }

    /**
     * Finds a case on the terrain.
     * 
     * @param lig the line of the wanted case
     * @param col the column of the wanted case
     * @return the wanted case
     */
    public Case getCase(int lig, int col) {
        if (lig < 0 || lig >= lignes || col < 0 || col >= colonnes)
            throw new IllegalArgumentException("la case à (" + lig + ", " + col + ") n'existe pas !");
        return cases[lig * colonnes + col];
    }

    /**
     * Gets all the cases as an Iterable, useful for drawing for example.
     * @return Iterable over the whole Array.
     */

    public Iterable<Case> getCases() {
        return Arrays.asList(cases);
    }

    /**
     * Add Given case on the terrain.
     * 
     * @param case
     */
    public void setCase(Case c) {
        this.cases[c.getLigne() * colonnes + c.getColonne()] = c;
    }

    /**
     * Checks if the given neighbor of the src case exists.
     * 
     * @param src origin case
     * @param dir direction to check the neighbor
     * @return true if there is a neighbor, false otherwise
     */
    public boolean voisinExiste(Case src, Direction dir) {
        switch (dir) {
            case NORD:
                return src.getLigne() > 0;
            case SUD:
                return src.getLigne() < lignes - 1;
            case EST:
                return src.getColonne() < colonnes - 1;
            case OUEST:
                return src.getColonne() > 0;
            default:
                throw new IllegalArgumentException("This is awkward...");
        }
    }

    /**
     * Gets a neighbor from a given case.
     * 
     * @param src origin case
     * @param dir direction to the neighbor
     * @return the neighbor
     */
    public Case getVoisin(Case src, Direction dir) {
        switch (dir) {
            case NORD:
                return getCase(src.getLigne() - 1, src.getColonne());
            case SUD:
                return getCase(src.getLigne() + 1, src.getColonne());
            case EST:
                return getCase(src.getLigne(), src.getColonne() + 1);
            case OUEST:
                return getCase(src.getLigne(), src.getColonne() - 1);
            default:
                throw new IllegalArgumentException("This is awkward...");
        }
    }

    /**
     * Return direction from src to dest.
     * 
     * @param src  origin case
     * @param dest destination case, must be a neighbour of src
     * @return the direction
     */
    public Direction getdir(Case src, Case dest) throws NotNeighboringCasesException {
        for (Direction dir : Direction.values()) {
            if (getVoisin(src, dir).equals(dest)) {
                return dir;
            }
        }
        throw new NotNeighboringCasesException("thrown from Carte.getdir");
    }

    /**
     * Return ArrayList with all available neigbors.
     * 
     * @param src Origin case
     * @return
     */
    public ArrayList<Case> getVoisins(Case src) {
        ArrayList<Case> voisins = new ArrayList<Case>();
        for (Direction dir : Direction.values()) {
            if (voisinExiste(src, dir)) {
                voisins.add(getVoisin(src, dir));
            }
        }
        return voisins;
    }

}
