package simu.evenements;

import robot.Action;
import robot.Robot;
import simu.Incendie;

import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;

public class InterventionEven extends RobotEven {
    Robot robot;

    Simulateur sim;

    public InterventionEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
        this.sim = sim;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);
    }

}
