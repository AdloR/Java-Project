package manage;

import java.util.List;

import exceptions.NotNeighboringCasesException;
import exceptions.UnreachableCaseException;
import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import simu.Simulateur;
import terrain.Carte;

public class ElementaryFirefighterChief extends FireFighterChief {
    public ElementaryFirefighterChief(List<Robot> robots, List<Incendie> incendies, Carte carte) {
        super(robots, incendies, carte);
    }

    public void affectRobot(Simulateur sim) {
        for (Incendie incendie : incendies) {
            while (incendie.getNbL() > 0) {
                for (int i = 0; i < robotsSize; i++) {
                    if (robots.get(i).isWaiting() && robots.get(i).isAccessible(incendie.getFireCase())) {
                        try {
                            Path path = robots.get(i).aStar(carte, robots.get(i).getPosition(), incendie.getFireCase());
                            long date = robots.get(i).followPath(sim, path, carte);
                            robots.get(i).intervenir(sim, date);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }

    }
}
