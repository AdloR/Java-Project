package simu.evenements.robot_evenements;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * Makes a robot intervene once on the wildfire (Incendie) on it's tile (Case).
 * This event happen at the end of the intervention time.
 */
public class InterventionEven extends RobotEven {
    private boolean smart = false;

    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * It can only be automatic, someone wanting to program a scenario might want to
     * use DebInterventionEvent.
     * 
     * @param date  The date to execute the event.
     * @param sim   The current simulator (Simulateur) instance.
     * @param robot The robot that will intervene.
     * 
     * @see DebInterventionEven
     */
    public InterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
        this.priority = true;
    }

    /**
     * The only difference with
     * {@link #InterventionEven(long, Simulateur, Robot)} is the smart value,
     * used for knowing if the robot should automatically go fill its
     * reservoir.
     * 
     * @param date  The date to execute the event.
     * @param sim   The current simulator (Simulateur) instance.
     * @param robot The robot that will intervene.
     * @param smart
     * @see #InterventionEven(long, Simulateur, Robot)
     */
    public InterventionEven(long date, Simulateur sim, Robot robot, boolean smart) {
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
                robot.intervenir(getSim(), this.getDate(), smart);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                System.err.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                        + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
            }
        }
        // Refill automatically if needed, only if smart is true
        if (smart && robot.getReservoir() <= 0) {
            Carte carte = getSim().getDonnees().getCarte();
            try {
                Path path = robot.Dijkstra(carte, robot.getPosition(),
                        (c) -> robot.findWater(c));
                robot.followPath(getSim(), path);
                robot.remplir(getSim());
            } catch (UnreachableCaseException e) {
                System.err.println("Robot could not refill, as no water is accessible.");
                System.err.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                        + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
                e.printStackTrace();
            }

        }
    }
}
