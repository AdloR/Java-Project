package simu.evenements;

import manage.FireFighterChief;
import simu.Simulateur;

public class LancementStrategie extends Evenement {
    int lancementProchain = 1;
    FireFighterChief chief;
    Simulateur sim;

    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief) {
        super(date);
        this.sim = sim;
        this.chief = chief;
    }

    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, int lancementProchain) {
        super(date);
        this.sim = sim;
        this.chief = chief;
        this.lancementProchain = lancementProchain;
    }

    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, boolean auto) {
        super(date, auto);
        this.sim = sim;
        this.chief = chief;
    }

    public LancementStrategie(long date, Simulateur sim, FireFighterChief chief, int lancementProchain, boolean auto) {
        super(date, auto);
        this.sim = sim;
        this.chief = chief;
        this.lancementProchain = lancementProchain;
    }

    @Override
    public void execute() {
        chief.affectRobot(sim);
        sim.ajouteEvenement(new LancementStrategie(sim.getDateSimulation() + lancementProchain, sim, chief, lancementProchain));
    }

}
