package simu.evenements;

import robot.Action;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;

public class InterventionEven extends RobotEven {
    Robot robot;
    Simulateur sim;

    public InterventionEven(long date, Robot robot, Simulateur sim) {
        super(date);
        this.robot = robot;
        this.sim = sim;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);
    }
}
