package simu.evenements.robot_evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public class RobotBougeEven extends RobotEven {
    Simulateur simu;

    Direction dir;

    public RobotBougeEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
        this.priority = true;
    }

    public RobotBougeEven(long date, Simulateur sim, Robot robot, Direction dir) {
        super(date, sim, robot);
        this.dir = dir;
        this.priority = true;
    }

    @Override
    public void execute() {
        Case c = robot.getPosition();
        Carte carte = c.getCarte();

        try {
            robot.setPosition(carte.getVoisin(c, this.dir));
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }
}
