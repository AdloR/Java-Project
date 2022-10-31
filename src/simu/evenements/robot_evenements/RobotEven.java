package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;

public abstract class RobotEven extends Evenement{
    private Simulateur sim;
    private int robotIndex;
    protected Robot robot;

    public RobotEven(long date, Simulateur sim, int robotIndex) {
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
