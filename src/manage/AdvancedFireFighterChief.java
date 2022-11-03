package manage;

import java.util.List;

import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

/**
 * The firefighterChief that will apply the Advanced strategy (second strategy of the topic)
 */
public class AdvancedFireFighterChief extends FireFighterChief {

    public AdvancedFireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    /**
     *
     * @param sim the Simulateur.
     * 1. The firefighterChief advises to all robots an "incendie" to extinct.
     * 2. The occupied robots refuse the proposition, the other ones compute the
     *            shortest route to go the "incendie" and return the time that they'll need to do so.
     * 3. The chief picks the fastest robot to extinct the "incendie".
     *            The robot chosen programs the events necessaries to make his way to the "incendie".
     *            Then he checks if the "incendie" was not extinct before pouring water.
     *            When the reservoir of a robot is empty, it goes refill it from his own.
     * 4. The chief can ask this for each "incendie". If some remain unassigned,
     *            the fire chief waits for a certain amount of time and
     *            waits for a certain period of time and proposes the remaining "incendie" again.
     */
    @Override
    public void affectRobot(Simulateur sim){
        for (Incendie incendie : incendies) {
            if (incendie.getNbL() > 0) {
                Robot fastestRobot = null;
                Path minPath = null;
                int minTime = 0;
                for (int i = 0; i < robotsSize; i++) {
                    Robot curRobot = robots.get(i);
                    if (curRobot.isWaiting(sim) && curRobot.isAccessible(incendie.getFireCase())) {
                        try {
                            Path path = curRobot.aStar(carte, curRobot.getPosition(), incendie.getFireCase());

                            if (fastestRobot == null || path.getDuration() < minTime) {
                                minTime = path.getDuration();
                                fastestRobot = curRobot;
                                minPath = path;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (fastestRobot != null) {
                        try {
                            long date = fastestRobot.followPath(sim, minPath, carte);
                            fastestRobot.intervenir(sim, date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }
}