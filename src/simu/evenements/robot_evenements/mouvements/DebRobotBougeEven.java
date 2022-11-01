package simu.evenements.robot_evenements.mouvements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Direction;

public class DebRobotBougeEven extends RobotEven {
    Direction dir;

    public DebRobotBougeEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
    }

    public DebRobotBougeEven(long date, Simulateur sim, Robot robot, Direction dir) {
        super(date, sim, robot);
        this.dir = dir;
    }

    @Override
    public void execute() {
        robot.startMove(getSim(), dir);
    }

}
