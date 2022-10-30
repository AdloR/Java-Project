package terrain;

import simu.Incendie;

public class Case {
    private Carte carte;
    private int coordLig;
    private int coordCol;
    private NatureTerrain type;
    private Incendie incendie = null;

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
