package manage;

import pathfinding.Path;
import robot.Robot;
import simu.Incendie;
import terrain.Carte;

import java.util.ArrayList;
import java.util.Iterator;

public class ElementaryFirefighterChief {
    private ArrayList<Robot> robots;
    private ArrayList<Incendie> incendies;
    private Carte carte;

    public ElementaryFirefighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies, Carte carte) {
        this.robots = robots;
        this.incendies = incendies;
        this.carte = carte
    }

    public void affectRobot() {
        Iterator<Incendie> it = incendies.iterator();
        int robotsSize = robots.size();
        while (it.hasNext()) {
            for (int i = 0; i < robotsSize; i++) {
                if (robots.get(i).isWaiting()) {
                    Path path = robots.get(i).aStar(carte, robots.get(i).getPosition(), it.next().getFireCase());
                    robots.get(i).followPath(path);
                }
                if (i == robotsSize - 1) {
                    i = 0;
                }
            }
        }
    }
}