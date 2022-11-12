package terrain;

import java.util.ArrayList;
import java.util.Arrays;

import exceptions.NotNeighboringCasesException;

/**
 * Represent a map which is essentially a matrix of tiles (Case).
 */
public class Carte {
    private int lignes, colonnes;
    private int tailleCases = 1;
    private Case[] cases;

    /**
     * Carte constructor. The setCase function should be called on each tile to
     * populate the matrix.
     * 
     * @param nbLignes    Vertical size of the map.
     * @param nbColonnes  Horizontal size of the map.
     * @param tailleCases Size of a tile's edge in m.
     */
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
     * Gets cases size.
     * 
     * @return cases' size
     */
    public int getTailleCases() {
        return tailleCases;
    }

    /**
     * Finds a tile (Case) on the terrain.
     * 
     * @param lig the line of the wanted tile.
     * @param col the column of the wanted tile.
     * @return the wanted tile.
     */
    public Case getCase(int lig, int col) {
        if (lig < 0 || lig >= lignes || col < 0 || col >= colonnes)
            throw new IllegalArgumentException("la case à (" + lig + ", " + col + ") n'existe pas !");
        return cases[lig * colonnes + col];
    }

    /**
     * Gets all the tiles (Case) as an Iterable.
     * 
     * @return Iterable over the whole Array.
     */

    public Iterable<Case> getCases() {
        return Arrays.asList(cases);
    }

    /**
     * Add Given tile (Case) on the terrain. This function should be called for the
     * hole terrain when instantiating the map (Carte).
     * 
     * @param case The tile (Case) to add. It's location is registered in the Case
     *             class.
     */
    public void setCase(Case c) {
        this.cases[c.getLigne() * colonnes + c.getColonne()] = c;
    }

    /**
     * Checks if the given neighbor of the src tile (Case) exists.
     * 
     * @param src origin tile (Case).
     * @param dir direction to check the neighbor.
     * @return true if there is a neighbor, false otherwise.
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
     * Gets a neighbor from a given tile (Case).
     * 
     * @param src origin tile (Case).
     * @param dir direction to the neighbor.
     * @return the neighbor tile (Case).
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
     * @param src  origin tile (Case).
     * @param dest destination tile (Case), must be a neighbour of src
     * @return the direction.
     */
    public Direction getdir(Case src, Case dest) throws NotNeighboringCasesException {
        for (Direction dir : Direction.values()) {
            if (voisinExiste(src, dir) && getVoisin(src, dir).equals(dest)) {
                return dir;
            }
        }
        throw new NotNeighboringCasesException("thrown from Carte.getdir");
    }

    /**
     * Return ArrayList with all available neigbors.
     * 
     * @param src Origin tile (Case).
     * @return src neighbors.
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
