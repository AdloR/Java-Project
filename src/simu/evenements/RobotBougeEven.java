package simu.evenements;

import robot.Robot;
import terrain.Case;

public class RobotBougeEven extends Evenement {
    Robot robot;
    Case dest;

    public RobotBougeEven(long date, Robot robot, Case dest) {
        super(date);
        this.robot = robot;
        this.dest = dest;
    }

    @Override
    public void execute() {
        robot.setPosition(dest);
    }

}
