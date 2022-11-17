package manage;

import java.util.List;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.DonneesSimulation;
import simu.Incendie;
import simu.Simulateur;

/**
 * The firefighter chief that will apply the elementary strategy (first strategy
 * of the topic).
 */
public class ElementaryFirefighterChief extends FireFighterChief {

    /**
     * <ul>
     * <li>The chief advises an unassigned {@code Incendie}, to a {@code Robot}.
     * <li>If the {@code Robot} is occupied, it refuses to go to the {@code Incendie}
     * advised,
     * else it looks if it's possible top reach the {@code Incendie} from its place.
     * if not, it also refuses.
     * <li>The chosen {@code Robot} will go to the case of {@code Incendie} and poor his
     * water.
     * <li>If its reservoir is empty, it remains as occupied to the chief and will
     * refuse all the new propositions.
     * <li>The chief goes back to steps 1-2-3 on another {@code Incendie} and so on
     * until all the
     * {@code Incendie}s are extincted.
     * </ul>
     * NB : in this strategy the {@code Robot}s won't go fill their reservoirs so if they're
     * all empty,
     * the only ones that could extinct the remaining fires would be the
     * {@code RobotAPattes}
     * (infinite size reservoir).
     *
     * @param sim the Simulateur.
     */
    @Override
    public void affectRobot(Simulateur sim) {
        DonneesSimulation donnees = sim.getDonnees();
        List<Incendie> incendies = donnees.getIncendies();
        List<Robot> robots = donnees.getRobots();
        for (Incendie incendie : incendies) {
            if (incendie.getNbL() > 0) {
                for (Robot robot : robots) {
                    if (robot.isWaiting(sim) && robot.isAccessible(incendie.getFireCase())) {
                        try {
                            /* search for shortest route with aStar algorithm. */
                            Path path = robot.Dijkstra(robot.getPosition(), (c) -> c.equals(incendie.getFireCase()));
                            robot.followPath(sim, path);
                            robot.startIntervention(sim, false);
                        } catch (UnreachableCaseException e) {
                            assert true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
