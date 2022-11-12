package terrain;

import simu.Incendie;

/**
 * Represent a tile in the map.
 */
public class Case {
    private Carte carte;
    private int coordLig;
    private int coordCol;
    private NatureTerrain type;
    private Incendie incendie = null;

    /**
     * Case constructor
     * 
     * @param carte Related map.
     * @param lig   Lign of the tile on the map.
     * @param col   Colum of the tile on the map.
     * @param type  Type of the tile's terrain.
     */
    public Case(Carte carte, int lig, int col, NatureTerrain type) {
        this.carte = carte;
        this.coordLig = lig;
        this.coordCol = col;
        this.type = type;
    }

    public int getLigne() {
        return coordLig;
    }

    public int getColonne() {
        return coordCol;
    }

    public Carte getCarte() {
        return carte;
    }

    public NatureTerrain getType() {
        return type;
    }

    public void setIncendie(Incendie incendie) {
        this.incendie = incendie;
    }

    public Incendie getIncendie() {
        return incendie;
    }
}
