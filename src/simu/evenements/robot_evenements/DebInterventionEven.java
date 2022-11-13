package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;

/**
 * An event (Evenement) to order a robot to stat an intervention at iven time.
 * 
 * @see InterventionEven
 */
public class DebInterventionEven extends RobotEven {

    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is a manual event, meaning it is kept in history for restart.
     * 
     * @param date       The date to execute the event.
     * @param sim        The current simulator (Simulateur) instance.
     * @param robotIndex The index of the robot in DonneesSimulation::getRobots()
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
     * @param date  Date of execution.
     * @param sim   Current simulator (Simulateur).
     * @param robot Robot to which the event applies.
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
