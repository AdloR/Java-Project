package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;

/**
 * Order a robot to start refill at given date.
 */
public class DebRemplissageEven extends RobotEven {
    /**
     * Creates an event that asks to start a refill.
     * 
     * It is a manual event, meaning it is kept in history for restart.
     * 
     * @param date       the date to execute the event
     * @param sim        the current Simulateur instance
     * @param robotIndex the index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     */
    public DebRemplissageEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
    }

    /**
     * Creates an event that asks to start a refill.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * 
     * @param date  date of execution
     * @param sim   current Simulateur
     * @param robot robot to which the event applies
     */
    public DebRemplissageEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
    }

    @Override
    public void execute() {
        robot.remplir(getSim());
    }

}
