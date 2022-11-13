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
     * TODO: Is it useful ?
     * 
     * @param date  Date of the event, when the strategy will be computed.
     * @param sim   Simulation on which the strategy will be proceeded.
     * @param chief FirefighterChief that will lead the strategy.
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief) {
        super(date);
        this.sim = sim;
        this.chief = chief;
    }

    /**
     * Constructor for repeated call.
     * 
     * @param date              Date of the event, when the strategy will be
     *                          computed.
     * @param sim               Simulation on which the strategy will be proceeded.
     * @param chief             FirefighterChief that will lead the strategy.
     * @param lancementProchain TODO: ???
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, int lancementProchain) {
        super(date);
        this.sim = sim;
        this.chief = chief;
        this.lancementProchain = lancementProchain;
    }

    /**
     * Constructor for initial call.
     * 
     * @param date  Date of the event, when the strategy will be computed.
     * @param sim   Simulation on which the strategy will be proceeded.
     * @param chief FirefighterChief that will lead the strategy.
     * @param auto  TODO: ???
     */
    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, boolean auto) {
        super(date, auto);
        this.sim = sim;
        this.chief = chief;
    }

    /**
     * TODO: Is it useful ?
     * 
     * @param date              Date of the event, when the strategy will be
     *                          computed.
     * @param sim               Simulation on which the strategy will be proceeded.
     * @param chief             FirefighterChief that will lead the strategy.
     * @param lancementProchain TODO: ???
     * @param auto              TODO: ???
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
