package simu.evenements;

import robot.Action;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;

public class RemplissageEven extends RobotEven {
    Simulateur sim;

    public RemplissageEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
        this.sim = sim;
    }

    @Override
    public void execute() {
        int toContinue = robot.remplir();
        for (int i = 1; i <= toContinue; i++) {
            sim.ajouteEvenement(new ContinuerEven(getDate() + i, robot, Action.REMPLISSAGE));
        }
    }

}
