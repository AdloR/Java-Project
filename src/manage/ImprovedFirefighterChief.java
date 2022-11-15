package manage;

import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;
import terrain.Case;

import java.util.List;

import exceptions.UnreachableCaseException;

public class ImprovedFirefighterChief extends FireFighterChief {

    /**
     * FireFighterChief constructor
     *
     * @param robots    List of robots of the map.
     * @param incendies List of {@code Incendie} of the map.
     * @param carte     The map ({@code Carte} type).
     */
    public ImprovedFirefighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    /**
     * <ul>
     * <li>The firefighterChief advises the nearest {@code incendie} to all robots
     * to extinct.
     *
     * <li>The occupied robots refuse the proposition, the other ones compute the
     * shortest route to go the {@code incendie} and return the time that they'll
     * need to do so.
     *
     * <li>The chief picks the fastest robot to extinct the {@code incendie}.
     * The robot chosen programs the events necessaries to make his way to the
     * {@code incendie}.
     * Then he checks if the {@code incendie} was not extinct before pouring water.
     * When the reservoir of a robot is empty, it goes refill it from his own.
     *
     * <li>The chief can ask this for each {@code incendie}. If some remain
     * unassigned,
     * the fire chief waits for a certain amount of time and
     * waits for a certain period of time and proposes the remaining
     * {@code incendie} again.
     *
     * </ul>
     *
     * @param sim the Simulateur.
     */
    @Override
    public void affectRobot(Simulateur sim) {
        for (Robot robot : robots) {
            if (robot.isWaiting(sim)) {
                try {
                    Path nearestIncendie = robot.Dijkstra(robot.getPosition(), this::CondIncendies);
                    robot.followPath(sim, nearestIncendie);
                    robot.startIntervention(sim);
                } catch (UnreachableCaseException e) {
                    assert true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean CondIncendies(Case c) {
        for (Incendie incendie : incendies) {
            if (c.equals(incendie.getFireCase()) && incendie.getNbL() > 0) {
                return true;
            }
        }
        return false;
    }
}
