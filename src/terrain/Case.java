package terrain;

public class Case {
    private Carte carte;
    private int coordLig;
    private int coordCol;
    private NatureTerrain type;

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
}
