package robot;

import exceptions.ForbiddenMoveException;
import exceptions.UnknownDirectionException;
import pathfinding.Path;
import pathfinding.SelfDriving;
import simu.Simulateur;
import simu.evenements.InterventionEven;
import simu.evenements.RemplissageEven;
import terrain.Case;
import terrain.Direction;
import terrain.Carte;

public abstract class Robot extends SelfDriving {
    protected Case position;
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

    private long timeFree = 0;
    private Simulateur simu;
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

    public int getReservoir() {
        return reservoir;
    }

    public void move(Direction dir) {

    }

    public abstract boolean isAccessible(Case position);

    /**
     * Intervene on fire. If the reservoir is not full enough, it will be emptied on
     * the fire;
     * 
     * @throws IllegalStateException
     * 
     *                               TODO : Javadoc
     */
    public void intervenir(Simulateur sim) throws IllegalStateException {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeIntervention;
        sim.ajouteEvenement(new InterventionEven(timeEnd, this, sim));
        this.timeFree = timeEnd;
        this.simu = sim;
    }

    /**
     * * Intervene on fire. If the reservoir is not full enough, it will be emptied
     * on
     * the fire;
     * 
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException
     */
    public void intervenir(Simulateur sim, long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException("The robot is already occupied !");
        }
        sim.ajouteEvenement(new InterventionEven(date + this.timeIntervention, this, sim));
        this.timeFree = date + this.timeIntervention;
        this.simu = sim;
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
     * 
     *                               TODO : Javadoc
     */
    public void remplir(Simulateur sim) {

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
        this.simu = sim;
    }

    /**
     * Refills the reservoir. If it was already filled, will still try to full it,
     * and therefore take unnecessary time.
     * 
     * @param date Precise the time at which the intervention should start.
     * @throws IllegalStateException in case there is no available water.
     */
    public void remplir(Simulateur sim, long date) {
        if (this.timeFree > date) {
            throw new IllegalStateException("The robot is already occupied !");
        }

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = date + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
        this.simu = sim;
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

    public void followPath(Path path, Carte carte) throws UnknownDirectionException {
        for (Direction direction : path.getPath()) {
            try {
                switch (direction) {
                    case NORD:
                        this.setPosition(carte.getVoisin(this.getPosition(), Direction.NORD));
                        break;
                    case SUD:
                        this.setPosition(carte.getVoisin(this.getPosition(), Direction.SUD));
                        break;
                    case EST:
                        this.setPosition(carte.getVoisin(this.getPosition(), Direction.EST));
                        break;
                    case OUEST:
                        this.setPosition(carte.getVoisin(this.getPosition(), Direction.OUEST));
                        break;
                    default:
                        throw new UnknownDirectionException("Unknown direction");
                }
            } catch (ForbiddenMoveException e) {
            }
        }
    }
}
