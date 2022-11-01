package simu.evenements.robot_evenements;

import robot.Robot;
import simu.evenements.Evenement;

public class RemplissageEven extends Evenement {
    Robot robot;

    public RemplissageEven(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        robot.remplirReservoir();
    }

}
