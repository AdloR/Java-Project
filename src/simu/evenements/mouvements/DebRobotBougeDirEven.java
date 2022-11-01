package simu.evenements.mouvements;

import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Direction;

public class DebRobotBougeDirEven extends RobotEven {
    Direction dir;

    public DebRobotBougeDirEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
    }

    @Override
    public void execute() throws IllegalStateException {
        robot.move(getSim(), dir, this.getDate());
    }

}
