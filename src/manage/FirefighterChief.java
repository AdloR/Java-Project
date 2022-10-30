package manage;

import robot.Robot;
import simu.Incendie;

import java.util.ArrayList;

public class FirefighterChief {
    private ArrayList<Robot> robots;
    private ArrayList<Incendie> incendies;

    public FirefighterChief(ArrayList<Robot> robots, ArrayList<Incendie> incendies) {
        this.robots = robots;
        this.incendies = incendies;
    }
}
