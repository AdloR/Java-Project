package robot;

import exceptions.ForbiddenMoveException;
import pathfinding.Path;
import pathfinding.SelfDriving;
import simu.Simulateur;
import simu.evenements.robot_evenements.InterventionEven;
import simu.evenements.robot_evenements.RemplissageEven;
import simu.evenements.robot_evenements.mouvements.RobotBougeEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public abstract class Robot extends SelfDriving {
    protected Case position;
    /* !! En m/s !! */
    protected int speed;
    protected int reservoirMax;
    protected int reservoir;
    protected int timeRefill;
    protected int volumeIntervention;
    protected int timeIntervention;

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int getSpeedOn(Case place) {
        return speed;
    }

    @Override
    public int getTimeOn(Case pos) {
        return pos.getCarte().getTailleCases() / getSpeedOn(pos);
    }

    public int deverserEau() {
        int tmpVol = Integer.min(volumeIntervention, reservoir);
        reservoir -= tmpVol;
        return tmpVol;
    }

    public int getReservoir() {
        return reservoir;
    }

    public void startMove(Simulateur sim, Direction dir) {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + getTimeOn(getPosition());
        System.out.println("FIN A " + timeEnd);
        sim.ajouteEvenement(new RobotBougeEven(timeEnd, sim, this, dir));
        this.timeFree = timeEnd;
    }

    // TODO : javadoc
    public void startMove(Simulateur sim, Direction dir, Case plannedCase) {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + getSpeedOn(plannedCase);
        System.out.println("FIN A " + timeEnd);
        sim.ajouteEvenement(new RobotBougeEven(timeEnd, sim, this, dir));
        this.timeFree = timeEnd;
    }

    // TODO : javadoc
    public void move(Simulateur sim, Direction dir, long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException(date + " : The robot can't start moving, it is already occupied !");
        }
        long timeMove = (this.position.getCarte().getTailleCases() / this.getSpeed());
        long timeEnd = date + timeMove;
        sim.ajouteEvenement(new RobotBougeEven(timeEnd, sim, this, dir));
        this.timeFree = timeEnd;
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
    public void startIntervention(Simulateur sim) {
        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeIntervention;
        sim.ajouteEvenement(new InterventionEven(timeEnd, sim, this));
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
    public void intervenir(Simulateur sim, long date) throws IllegalStateException {
        if (this.timeFree > date) {
            throw new IllegalStateException(date + " : The robot can't intervene, it is already occupied !");
        }
        sim.ajouteEvenement(new InterventionEven(date + this.timeIntervention, sim, this));
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
     * 
     *                               TODO : Javadoc
     */
    public void remplir(Simulateur sim) {

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = Long.max(this.timeFree, sim.getDateSimulation()) + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
        this.timeFree = timeEnd;
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
            throw new IllegalStateException(date + " : The robot can't refill, it is already occupied !");
        }

        if (!this.findWater())
            throw new IllegalStateException("There is no water accessible for the robot !");

        long timeEnd = date + this.timeRefill;
        sim.ajouteEvenement(new RemplissageEven(timeEnd, this));
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
    public boolean isWaiting(Simulateur sim) {
        return sim.getDateSimulation() >= this.timeFree;
    }

    /**
     * !!! Ajouter déverser eau à la fin !!!
     */
    public long followPath(Simulateur sim, Path path, Carte carte) {
        Case plannedCase = path.getStart();
        for (Direction direction : path.getPath()) {
            this.startMove(sim, direction, plannedCase);
            plannedCase = carte.getVoisin(plannedCase, direction);
        }
        return timeFree;
    }
}
