package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;

/**
 * An event (Evenement) to order a robot to stat an intervention at iven time.
 * 
 * @see InterventionEven
 */
public class DebInterventionEven extends RobotEven {
    private boolean smart = false;

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

    /**
     * The only difference with
     * {@link #DebInterventionEven(long, Simulateur, int)} is the smart value,
     * used for knowing if the robot should automatically go fill its
     * reservoir.
     * 
     * @param date       The date to execute the event.
     * @param sim        The current simulator (Simulateur) instance.
     * @param robotIndex The index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     * @param smart      if {@code true}, the robot will refill automatically.
     * @see #DebInterventionEven(long, Simulateur, int)
     */
    public DebInterventionEven(long date, Simulateur sim, int robotIndex, boolean smart) {
        super(date, sim, robotIndex);
        this.smart = smart;
    }

    /**
     * The only difference with
     * {@link #DebInterventionEven(long, Simulateur, Robot)} is the smart value,
     * used for knowing if the robot should automatically go fill its
     * reservoir.
     * 
     * @param date  The date to execute the event.
     * @param sim   The current simulator (Simulateur) instance.
     * @param robot Robot to which the event applies.
     * @param smart if {@code true}, the robot will refill automatically.
     * @see #DebInterventionEven(long, Simulateur, Robot)
     */
    public DebInterventionEven(long date, Simulateur sim, Robot robot, boolean smart) {
        super(date, sim, robot);
        this.smart = smart;
    }

    @Override
    public void execute() {
        try {
            robot.intervenir(getSim(), this.getDate(), smart);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                    + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
        }
    }

}
