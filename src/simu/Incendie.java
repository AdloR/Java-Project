package simu;

import terrain.Case;

public class Incendie {
    /**
     * @param fireCase position of fire.
     * @param nbL nb of litters to extinct fire.
     */
    private Case fireCase;
    private int nbL;

    public Incendie(Case fireCase, int nbL) {
        this.fireCase = fireCase;
        this.nbL = nbL;
    }

    public String toString() {
        return "Incendie{" +
                "fireCase=" + fireCase +
                ", nbL=" + nbL +
                '}';
    }

    public Case getFireCase() {
        return fireCase;
    }

    public void setFireCase(Case fireCase) {
        this.fireCase = fireCase;
    }

    public int getNbL() {
        return nbL;
    }

    public void setNbL(int nbL) {
        this.nbL = nbL;
    }
}
