package robot;

import simu.Incendie;
import terrain.Case;
import terrain.NatureTerrain;

public abstract class Robot {
    protected Case position;
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

    private int timeCurrentAction = 0;
    private Action currentAction = Action.ATTENTE;

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) {
        this.position = position;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int deverserEau(int vol) {
        int tmpVol = Integer.min(vol, reservoir);
        reservoir -= tmpVol;
        return tmpVol;
    }

    /**
     * Intervene on current fire (the fire on the position case). If the reservoir
     * is not full enough, it will be emptied on the fire;
     * 
     * @return time left for emptying the reservoir or setting off the fire.
     * @throws IllegalStateException in case of no fire present on the current
     *                               case.
     * @throws IllegalStateException if the robot was already doing something
     *                               else, such as moving.
     */
    public int intervenir() {
        if (currentAction != Action.ATTENTE && currentAction != Action.INTERVENTION)
            throw new IllegalStateException("The robot is already occupied !");

        Incendie incendie = getPosition().getIncendie();
        if (incendie == null)
            throw new IllegalStateException("There is no fire under the robot !");

        if (timeCurrentAction == 0) { // Starting to intervene
            currentAction = Action.INTERVENTION;
            timeCurrentAction = timeIntervention;
        } else // Merely decreasing time left
            timeCurrentAction--;

        if (timeCurrentAction == 0) { // This time it means we have ended
            currentAction = Action.ATTENTE;
            int used_water = Integer.min(reservoir, incendie.getNbL());
            deverserEau(used_water);
            incendie.setNbL(incendie.getNbL() - used_water);
        }

        return timeCurrentAction;
    }

    /**
     * Refills the reservoir. If it was already filled, will still try to full it, and
     * therefore take unnecessary time.
     * 
     * @return time left for emptying the reservoir or setting off the fire.
     * @throws IllegalStateException in case the current case is not water.
     * @throws IllegalStateException if the robot was already doing something
     *                               else, such as moving.
     */
    public int remplir() {
        if (currentAction != Action.ATTENTE && currentAction != Action.REMPLISSAGE)
            throw new IllegalStateException("The robot is already occupied !");

        if (getPosition().getType() != NatureTerrain.EAU)
            throw new IllegalStateException("There is no water under the robot !");

        if (timeCurrentAction == 0) { // Starting to intervene
            currentAction = Action.REMPLISSAGE;
            timeCurrentAction = timeRefill;
        } else // Merely decreasing time left
            timeCurrentAction--;

            System.out.println(timeCurrentAction);

        if (timeCurrentAction == 0) { // This time it means we have ended
            currentAction = Action.ATTENTE;
            remplirReservoir();
        }

        return timeCurrentAction;
    }

    public void remplirReservoir() {
        reservoir = reservoirMax;
    }
}
