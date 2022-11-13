package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;

/**
 * Event for robot action.
 */
public abstract class RobotEven extends Evenement {
    private Simulateur sim;
    private int robotIndex;
    protected Robot robot;

    /**
     * Creates a manual RobotEven.
     * 
     * @param date       date of execution
     * @param sim        current Simulateur
     * @param robotIndex index of robot to which the event applies in
     *                   DonneesSimulation::getRobots()
     * 
     * @see isAuto()
     */
    public RobotEven(long date, Simulateur sim, int robotIndex) {
        super(date, false);
        this.sim = sim;
        this.robotIndex = robotIndex;
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    /**
     * Creates an automatic RobotEven.
     * 
     * @param date  date of execution
     * @param sim   current Simulateur
     * @param robot robot to which the event applies
     * 
     * @see isAuto()
     */
    public RobotEven(long date, Simulateur sim, Robot robot) {
        super(date, true);
        this.sim = sim;
        this.robotIndex = -1;
        this.robot = robot;
    }

    /**
     * Enables Simulateur to find the new Robot instance for a manual Evenement
     * without recreating the Evenement.
     */
    public void actualiserRobots() {
        if (!isAuto())
            this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    /**
     * Getter for sim.
     * 
     * @return current Simulateur instance
     */
    protected Simulateur getSim() {
        return sim;
    }

}
