package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;

/**
 * An Evenement to call InterventionEvent at the right time.
 * This is merely a way to create time.
 * 
 * @see InterventionEven
 */
public class DebInterventionEven extends RobotEven {

    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is a manual event, meaning it is kept in history for restart.
     * 
     * @param date       the date to execute the event
     * @param sim        the current Simulateur instance
     * @param robotIndex the index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     */
    public DebInterventionEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
    }

    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * 
     * @param date  date of execution
     * @param sim   current Simulateur
     * @param robot robot to which the event applies
     */
    public DebInterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
    }

    @Override
    public void execute() {
        try {
            robot.intervenir(getSim(), this.getDate());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                    + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
        }
    }

}
