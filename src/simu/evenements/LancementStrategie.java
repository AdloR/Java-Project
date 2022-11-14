package simu.evenements;

import manage.FireFighterChief;
import simu.Simulateur;

/**
 * Event called to start a strategy.
 */
public class LancementStrategie extends Evenement {
    int lancementProchain = 1;
    FireFighterChief chief;
    Simulateur sim;

    /**
     * Automatic Event called to start a strategy. It will create a new
     * LancementStrategie to be processed on the next iteration.
     * 
     * @param date  Date of the event, when the strategy will be computed.
     * @param sim   Simulation on which the strategy will be proceeded.
     * @param chief FirefighterChief that will lead the strategy.
     * 
     * @see Evenement
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief,
     *      int lancementProchain)
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief,
     *      boolean auto)
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief) {
        super(date);
        this.sim = sim;
        this.chief = chief;
    }

    /**
     * Automatic Event called to start a strategy. It will create a new
     * LancementStrategie to be processed after lancementProchain iterations. This
     * is usefull for avoiding a costly calculation at every step.
     * 
     * @param date              Date of the event, when the strategy will be
     *                          computed.
     * @param sim               Simulation on which the strategy will be proceeded.
     * @param chief             FirefighterChief that will lead the strategy.
     * @param lancementProchain Iterations to do before processing the next
     *                          LancementStrategie.
     * 
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief)
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief,
     *      int lancementProchain, boolean auto)
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, int lancementProchain) {
        super(date);
        this.sim = sim;
        this.chief = chief;
        this.lancementProchain = lancementProchain;
    }

    /**
     * Event called to start a strategy. It will create a new
     * automatic LancementStrategie to be processed on the next iteration. This is
     * usefull for the first iteration only, as none of the other Events should be
     * kept in the history, and in this case auto should be set as false.
     * 
     * @param date  Date of the event, when the strategy will be computed.
     * @param sim   Simulation on which the strategy will be proceeded.
     * @param chief FirefighterChief that will lead the strategy.
     * @param auto  True if the Event is automatic, false otherwise. False means the
     *              Event is kept in history for restart.
     * 
     * @see Evenement
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief,
     *      int lancementProchain)
     * @see #LancementStrategie(long date, Simulateur sim, FireFighterChief chief,
     *      boolean auto)
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, boolean auto) {
        super(date, auto);
        this.sim = sim;
        this.chief = chief;
    }

    /**
     * Event called to start a strategy. It will create a new
     * LancementStrategie to be processed after lancementProchain iterations. This
     * is usefull in the case of the first Evenement created manually, and to avoid
     * doing costly operations every tick.
     * 
     * @param date              Date of the event, when the strategy will be
     *                          computed.
     * @param sim               Simulation on which the strategy will be proceeded.
     * @param chief             FirefighterChief that will lead the strategy.
     * @param lancementProchain Iterations to do before processing the next
     *                          LancementStrategie.
     * @param auto  True if the Event is automatic, false otherwise. False means the
     *              Event is kept in history for restart.
     * 
     * @see Evenement
     * @see #LancementStrategie(long, Simulateur, FireFighterChief, boolean)
     * @see #LancementStrategie(long, Simulateur, FireFighterChief, int)
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, int lancementProchain, boolean auto) {
        super(date, auto);
        this.sim = sim;
        this.chief = chief;
        this.lancementProchain = lancementProchain;
    }

    @Override
    public void execute() {
        chief.affectRobot(sim);
        sim.ajouteEvenement(
                new LancementStrategie(sim.getDateSimulation() + lancementProchain, sim, chief, lancementProchain));
    }

}
