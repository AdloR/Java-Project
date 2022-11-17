package manage;

import java.util.List;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.DonneesSimulation;
import simu.Simulateur;

public class ImprovedFirefighterChief extends FireFighterChief {

    /**
     * <ul>
     * <li>The firefighterChief advises the nearest {@code Incendie} to all
     * {@code Robot}s
     * to extinct.
     *
     * <li>The occupied {@code Robot}s refuse the proposition, the other ones
     * compute the
     * shortest route to go the {@code Incendie} and return the time that they'll
     * need to do so.
     *
     * <li>The chief picks the nearest {@code Incendie} from the
     * {@code Robot}.
     * The chosen{@code Robot} programs the events necessaries to make his way to
     * the
     * {@code Incendie}.
     * Then he checks if the {@code Incendie} was not extinct before pouring water.
     * When the reservoir of a {@code Robot} is empty, it goes refill it from his
     * own.
     *
     * <li>The chief can ask this for each {@code Robot}s. If some remain
     * unassigned,
     * the fire chief waits for a certain amount of and proposes the remaining {@code Incendie}s to the
     * {@code Robot}s again.
     *
     * </ul>
     *
     * @param sim the Simulateur.
     */
    @Override
    public void affectRobot(Simulateur sim) {
        DonneesSimulation donnees = sim.getDonnees();
        List<Robot> robots = donnees.getRobots();
        for (Robot robot : robots) {
            if (robot.isWaiting(sim)) {
                try {
                    Path nearestIncendie = robot.Dijkstra(robot.getPosition(),
                            (c) -> (c.getIncendie() != null && c.getIncendie().getNbL() > 0));
                    robot.followPath(sim, nearestIncendie);
                    robot.startIntervention(sim, true);
                } catch (UnreachableCaseException e) {
                    assert true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
