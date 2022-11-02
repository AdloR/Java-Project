package manage;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import terrain.Carte;

import java.util.ArrayList;

public class ElementaryFirefighterChief extends FireFighterChief {
    public ElementaryFirefighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    public void affectRobot() throws UnreachableCaseException, NotNeighboringCasesException {
        for (Incendie incendie : incendies) {
            while (incendie.getNbL() > 0) {
                for (int i = 0; i < robotsSize; i++) {
                    if (robots.get(i).isWaiting() && robots.get(i).isAccessible(incendie.getFireCase())) {
                        Path path = robots.get(i).aStar(carte, robots.get(i).getPosition(), incendie.getFireCase());
                        robots.get(i).followPath(path, carte);
                        break;
                    }
                    if (i == robotsSize - 1) {
                        i = 0;
                    }
                }
            }

        }
    }
}