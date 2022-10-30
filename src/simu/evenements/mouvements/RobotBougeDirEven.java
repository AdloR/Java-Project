package simu.evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.evenements.Evenement;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public class RobotBougeDirEven extends Evenement {
    Robot robot;
    Direction dir;

    public RobotBougeDirEven(long date, Robot robot, Direction dir) {
        super(date);
        this.robot = robot;
        this.dir = dir;
    }

    @Override
    public void execute() {
        Case c = robot.getPosition();
        int x = c.getColonne();
        int y = c.getLigne();
        Carte carte = c.getCarte();

        try {
            robot.setPosition(carte.getVoisin(c, dir));
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }
    
}
