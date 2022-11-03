package manage;

import java.util.List;

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
            if (incendie.getNbL() > 0) {
                for (Robot robot : robots) {
                    if (robot.isWaiting(sim) && robot.isAccessible(incendie.getFireCase())) {
                        try {
                            Path path = robot.aStar(carte, robot.getPosition(), incendie.getFireCase());
                            robot.followPath(sim, path, carte);
                            robot.startIntervention(sim);
                        } catch (UnreachableCaseException e) {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
