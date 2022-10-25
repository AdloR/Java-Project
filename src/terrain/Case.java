package terrain;

public class Case {
    private int coordLig;
    private int coordCol;
    private NatureTerrain type;

    public int getLigne() {
        return coordLig;
    }
    public int getColonne() {
        return coordCol;
    }
    public NatureTerrain getType() {
        return type;
    }
}
