package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;

public abstract class RobotEven extends Evenement {
    private Simulateur sim;
    private int robotIndex;
    protected Robot robot;

    public RobotEven(long date, Simulateur sim, int robotIndex) {
        super(date, false);
        this.sim = sim;
        this.robotIndex = robotIndex;
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    public RobotEven(long date, Simulateur sim, Robot robot) {
        super(date, true);
        this.sim = sim;
        this.robotIndex = -1;
        this.robot = robot;
    }

    public void actualiserRobots() {
        if (!isAuto())
            this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    protected Simulateur getSim() {
        return sim;
    }

}
