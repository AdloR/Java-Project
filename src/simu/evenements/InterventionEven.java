package simu.evenements;

import robot.Robot;
import simu.Incendie;

public class InterventionEven extends Evenement {

    Robot robot;

    public InterventionEven(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);
    }

}
