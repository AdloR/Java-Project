package robot;

import exceptions.ForbiddenMoveException;
import pathfinding.SelfDriving;
import simu.Incendie;
import simu.Simulateur;
import simu.evenements.ContinuerEven;
import simu.evenements.InterventionEven;
import terrain.Case;
import terrain.Direction;

public abstract class Robot extends SelfDriving {
    protected Case position;
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

    private Simulateur simu;
    private long timeFree = 0;
    // private Action currentAction = Action.ATTENTE;

    public Case getPosition() {
        return position;
    }

    public void setPosition(Case position) throws ForbiddenMoveException {
        this.position = position;
    }

    public int getSpeed() {
        return getSpeedOn(this.position);
    }

    @Override
    public int getSpeedOn(Case place) {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int deverserEau() {
        int tmpVol = Integer.min(volumeIntervention, reservoir);
        reservoir -= tmpVol;
        return tmpVol;
    }

    public void move(Direction dir) {

    }

    /**
     * Intervene on fire. If the reservoir is not full enough, it will be emptied on
     * the fire;
     * 
     * @param incendie The wildfire on which to intervene.
     * @throws IllegalStateException
     */
    public void intervenir(Incendie incendie) throws IllegalStateException {
        long timeEnd = Long.max(this.timeFree, simu.getDateSimulation()) + this.timeIntervention;
        this.simu.ajouteEvenement(new InterventionEven(timeEnd, this, simu));
        this.timeFree = timeEnd;
    }

    /**
     * * Intervene on fire. If the reservoir is not full enough, it will be emptied
     * on
     * the fire;
     * 
     * @param incendie The wildfire on which to intervene.
     * @param date     Precise the time at which the intervention should start.
     * @throws IllegalStateException
     */
    public void intervenir(Incendie incendie, long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException("The robot is already occupied !");
        }
        this.simu.ajouteEvenement(new InterventionEven(date + this.timeIntervention, this, simu));
        this.timeFree = date + this.timeIntervention;
    }

    /**
     * Return True if there is water accessible.
     * 
     * @return the boolean.
     */
    protected abstract boolean findWater();

    /**
     * Refills the reservoir. If it was already filled, will still try to full it,
     * and
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

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

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

    /**
     * A robot can be occupied either by extinguishing a wildfire, by moving or by
     * filling up.
     * 
     * @return True if the robot is not occupied.
     */
    public boolean isWaiting() {
        return this.simu.getDateSimulation() >= this.timeFree;
    }
}
