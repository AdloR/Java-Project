package manage;

import exceptions.NotNeighboringCasesException;
import exceptions.UnknownDirectionException;
import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

import java.util.ArrayList;

public class ElementaryFirefighterChief extends FireFighterChief {
    public ElementaryFirefighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    public void affectRobot(Simulateur sim) throws UnknownDirectionException, UnreachableCaseException, NotNeighboringCasesException {
        for (Incendie incendie : incendies) {
            while (incendie.getNbL() > 0) {
                for (int i = 0; i < robotsSize; i++) {
                    if (robots.get(i).isWaiting() && robots.get(i).isAccessible(incendie.getFireCase())) {
                        Path path = robots.get(i).aStar(carte, robots.get(i).getPosition(), incendie.getFireCase());
                        long date = robots.get(i).followPath(sim, path, carte);
                        robots.get(i).intervenir(sim, date);
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