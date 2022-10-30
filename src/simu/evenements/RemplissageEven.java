package simu.evenements;

import robot.Action;
import robot.Robot;
import simu.Simulateur;

public class RemplissageEven extends Evenement {
    Robot robot;
    Simulateur sim;

    public RemplissageEven(long date, Robot robot, Simulateur sim) {
        super(date);
        this.robot = robot;
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
