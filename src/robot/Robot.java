package robot;

import exceptions.ForbiddenMoveException;
import pathfinding.SelfDriving;
import simu.Simulateur;
import simu.evenements.InterventionEven;
import simu.evenements.RemplissageEven;
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

    public abstract boolean isAccessible(Case position);

    /**
     * Intervene on fire. If the reservoir is not full enough, it will be emptied on
     * the fire;
     * 
     * @throws IllegalStateException
     */
    public void intervenir() throws IllegalStateException {
        long timeEnd = Long.max(this.timeFree, simu.getDateSimulation()) + this.timeIntervention;
        this.simu.ajouteEvenement(new InterventionEven(timeEnd, this));
        this.timeFree = timeEnd;
    }

    /**
     * * Intervene on fire. If the reservoir is not full enough, it will be emptied
     * on
     * the fire;
     * 
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException
     */
    public void intervenir(long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException("The robot is already occupied !");
        }
        this.simu.ajouteEvenement(new InterventionEven(date + this.timeIntervention, this));
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
     * and therefore take unnecessary time.
     * 
     * @throws IllegalStateException in case there is no available water.
     */
    public void remplir() {

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = Long.max(this.timeFree, simu.getDateSimulation()) + this.timeRefill;
        this.simu.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
    }

    /**
     * Refills the reservoir. If it was already filled, will still try to full it,
     * and therefore take unnecessary time.
     * 
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException in case there is no available water.
     */
    public void remplir(long date) {
        if (this.timeFree > date) {
            throw new IllegalStateException("The robot is already occupied !");
        }

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = date + this.timeRefill;
        this.simu.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
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
    public boolean isWaiting(Simulateur simu) {
        return simu.getDateSimulation() >= this.timeFree;
    }
}
