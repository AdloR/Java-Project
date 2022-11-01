package manage;

import exceptions.NotNeighboringCasesException;
import exceptions.UnknownDirectionException;
import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import terrain.Carte;

import java.util.*;

public class AdvancedFireFighterChief extends FireFighterChief {
    private final int incendiesSize;

    public AdvancedFireFighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
        this.incendiesSize = incendies.size();
    }

    @Override
    public void affectRobot() throws UnknownDirectionException, UnreachableCaseException, NotNeighboringCasesException {
        for (int j = 0; j < incendiesSize; j++) {
            Incendie incendie = incendies.get(j);
            if (incendie.getNbL() > 0) {
                Robot fastestRobot = null;
                Path minPath = null;
                int minTime = 0;
                for (int i = 0; i < robotsSize; i++) {
                    Robot curRobot = robots.get(i);
                    if (curRobot.isWaiting() && curRobot.isAccessible(incendie.getFireCase())) {
                        Path path = curRobot.aStar(carte, curRobot.getPosition(), incendie.getFireCase());
                        if (fastestRobot == null) {
                            minTime = path.getDuration();
                            fastestRobot = curRobot;
                            minPath = path;
                            continue;
                        }
                        if (path.getDuration() < minTime) {
                            fastestRobot = curRobot;
                            minPath = path;
                        }
                    }
                }
                if (fastestRobot != null) {
                    fastestRobot.followPath(minPath, carte);
                }
            }
            if (incendie.getNbL() == 0) {
                incendies.remove(j);
                j--;
            }
            if (j == incendiesSize - 1 && !incendies.isEmpty()) {
                j = 0;
            }

        }
    }
}