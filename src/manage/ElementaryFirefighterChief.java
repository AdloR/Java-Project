package manage;

import java.util.List;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * The firefighter chief that will apply the elementary strategy (first strategy
 * of the topic).
 */
public class ElementaryFirefighterChief extends FireFighterChief {
    public ElementaryFirefighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    /**
     *
     * @param sim the Simulateur.
     *            1. The chief advises an unassigned "incendie", to a robot.
     *            2. If the robot is occupied, it refuses to go to the "incendie"
     *            advised,
     *            else it looks if it's possible top reach the "incendie" from its
     *            place.
     *            if not, it also refuses.
     *            3. The chosen robot will go to the case of "incendie" and poor his
     *            water.
     *            4. If its reservoir is empty, it remains as occupied to the chief
     *            and will
     *            refuse all the new propositions.
     *            5. The chief go back to steps 1-2-3 on another "incendie" and so
     *            on until all the
     *            "incendie" are extincted.
     *            NB : in this strategy the robots won't go fill their reservoirs so
     *            if they're all empty,
     *            the only ones that could extinct the remaining fires would be the
     *            "RobotAPattes"
     *            (infinite size reservoir).
     */

    @Override
    public void affectRobot(Simulateur sim) {
        for (Incendie incendie : incendies) {
            if (incendie.getNbL() > 0) {
                for (Robot robot : robots) {
                    if (robot.isWaiting(sim) && robot.isAccessible(incendie.getFireCase())) {
                        try {
                            /* search for shortest route with aStar algorithm. */
                            Path path = robot.Dijkstra(robot.getPosition(),
                                    (c) -> c.equals(incendie.getFireCase()));
                            robot.followPath(sim, path);
                            robot.startIntervention(sim);
                        } catch (UnreachableCaseException e) {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
