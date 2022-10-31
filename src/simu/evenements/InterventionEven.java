package simu.evenements;

import gui.Simulable;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;

public class InterventionEven extends Evenement {

    Simulateur simu;
    Robot robot;

    public InterventionEven(long date, Robot robot, Simulateur simu) {
        super(date);
        this.robot = robot;
        this.simu = simu;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);

        /* Redirect toward next task */
        // if (robot.getReservoir() <= 0)
        // robot.remplir(simu);
        // TODO: redirection
        // if (robot.getPosition().getIncendie().getNbL() > 0) {
        //     robot.intervenir(simu);
        }
    }
}
