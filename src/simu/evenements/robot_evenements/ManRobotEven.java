package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;

public abstract class ManRobotEven extends Evenement {
    private Simulateur sim;
    private int robotIndex;
    protected Robot robot;

    public ManRobotEven(long date, Simulateur sim, int robotIndex) {
        super(date);
        this.sim = sim;
        this.robotIndex = robotIndex;
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    public void actualiserRobots() {
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    protected Simulateur getSim() {
        return sim;
    }

}
