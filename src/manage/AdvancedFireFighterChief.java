package manage;

import java.util.List;

import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.DonneesSimulation;
import simu.Incendie;
import simu.Simulateur;

/**
 * The firefighterChief that will apply the Advanced strategy (second strategy
 * of the topic)
 */
public class AdvancedFireFighterChief extends FireFighterChief {

    /**
     * <ul>
     * <li>The firefighterChief advises to all robots an {@code Incendie} to
     * extinct.
     *
     * <li>The occupied {@code Robot}s refuse the proposition, the other ones
     * compute the
     * shortest route to go the {@code Incendie} and return the time that they'll
     * need to do so.
     *
     * <li>The chief picks the fastest {@code Robot} to extinct the
     * {@code Incendie}.
     * The robot chosen programs the events necessaries to make his way to the
     * {@code Incendie}.
     * Then he checks if the {@code Incendie} was not extinct before pouring water.
     * When the reservoir of a {@code Robot} is empty, it goes refill it from his
     * own.
     *
     * <li>The chief can ask this for each {@code Incendie}. If some remain
     * unassigned,
     * the fire chief waits for a certain amount of time and the remaining
     * {@code Incendie}s again.
     *
     * </ul>
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
                Robot fastestRobot = null;
                Path minPath = null;
                int minTime = 0;
                for (Robot robot : robots) {
                    if (robot.isWaiting(sim) && robot.isAccessible(incendie.getFireCase())) {
                        try {
                            Path path = robot.Dijkstra(robot.getPosition(), (c) -> c.equals(incendie.getFireCase()));
                            if (fastestRobot == null || path.getDuration() < minTime) {
                                minTime = path.getDuration();
                                fastestRobot = robot;
                                minPath = path;
                            }
                        } catch (UnreachableCaseException e) {
                            assert true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (fastestRobot != null) {
                    try {
                        fastestRobot.followPath(sim, minPath);
                        fastestRobot.startIntervention(sim, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}