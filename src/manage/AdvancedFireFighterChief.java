package manage;

import java.util.List;

import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * The firefighterChief that will apply the Advanced strategy (second strategy
 * of the topic) :
 * 1. The firefighterChief advises to all robots an {@code Incendie} to extinct.
 * 2. The occupied robots refuse the proposition, the other ones compute the
 * shortest route to go the {@code Incendie} and return the time that they'll
 * need to do so.
 * 3. The chief picks the fastest robot to extinct the {@code Incendie}. The
 * robot chosen programs the events necessaries to make his way to the
 * {@code Incendie}. Then he checks if the {@code Incendie} was not extinct
 * before pouring water. When the reservoir of a robot is empty, it goes refill
 * it from his own.
 * 4. The chief can ask this for each {@code Incendie}. If some remain
 * unassigned, the fire chief waits for a certain amount of time and waits for a
 * certain period of time and proposes the remaining {@code Incendie} again.
 */
public class AdvancedFireFighterChief extends FireFighterChief {
    public AdvancedFireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    @Override
    public void affectRobot(Simulateur sim) {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (fastestRobot != null) {
                    try {
                        fastestRobot.followPath(sim, minPath);
                        fastestRobot.startIntervention(sim);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}