package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Incendie;
import simu.Simulateur;

/**
 * Makes a robot intervene once on the wildfire (Incendie) on it's tile (Case).
 * This event happen at the end of the intervention time.
 */
public class InterventionEven extends RobotEven {
    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * It can only be automatic, someone wanting to program a scenario might want to
     * use DebInterventionEvent.
     * 
     * @param date       The date to execute the event.
     * @param sim        The current simulator (Simulateur) instance.
     * @param robotIndex The index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     * 
     * @see DebInterventionEven
     */
    public InterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
        this.priority = true;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);

        /* Redirect toward next task */
        if (incendie.getNbL() > 0 && robot.getReservoir() > 0) {
            try {
                robot.intervenir(getSim(), this.getDate());
            } catch (IllegalStateException e) {
                e.printStackTrace();
                System.out.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                        + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
            }
        }
        if (robot.getReservoir() <= 0) {
        }
    }
}
