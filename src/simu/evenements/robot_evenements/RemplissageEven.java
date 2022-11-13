package simu.evenements.robot_evenements;

import robot.Robot;
import simu.evenements.Evenement;

/**
 * Makes a robot refill it's reservoir. This Event happen at the end of the
 * refill time.
 */
public class RemplissageEven extends Evenement {
    Robot robot;

    public RemplissageEven(long date, Robot robot) {
        super(date);
        this.robot = robot;
        this.priority = true;
    }

    @Override
    public void execute() {
        robot.remplirReservoir();
    }

}
