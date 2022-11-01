package simu.evenements.robot_evenements;

import robot.Robot;
import simu.evenements.AutoEven;

public abstract class AutoRobotEven extends AutoEven {
    protected Robot robot;

    public AutoRobotEven(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

}
