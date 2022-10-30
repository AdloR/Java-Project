package simu.evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.evenements.Evenement;
import terrain.Case;

public class RobotTeleportEven extends Evenement {
    Robot robot;
    Case dest;

    public RobotTeleportEven(long date, Robot robot, Case dest) {
        super(date);
        this.robot = robot;
        this.dest = dest;
    }

    @Override
    public void execute() throws ForbiddenMoveException {
        robot.setPosition(dest);
    }

}
