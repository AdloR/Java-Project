package simu.evenements;

import robot.Action;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;

public class InterventionEven extends RobotEven {
    Simulateur sim;

    public InterventionEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
        this.sim = sim;
    }

    @Override
    public void execute() {
        int toContinue = robot.intervenir();
        for (int i = 1; i <= toContinue; i++) {
            sim.ajouteEvenement(new ContinuerEven(getDate() + i, robot, Action.INTERVENTION));
        }
    }

}
