package manage;

import java.util.List;

import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

public class AdvancedFireFighterChief extends FireFighterChief {

    public AdvancedFireFighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    @Override
    public void affectRobot(Simulateur sim){
        for (Incendie incendie : incendies) {
            if (incendie.getNbL() > 0) {
                Robot fastestRobot = null;
                Path minPath = null;
                int minTime = 0;
                for (int i = 0; i < robotsSize; i++) {
                    Robot curRobot = robots.get(i);
                    if (curRobot.isWaiting() && curRobot.isAccessible(incendie.getFireCase())) {
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