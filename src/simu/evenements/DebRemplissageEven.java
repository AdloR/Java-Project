package simu.evenements;

import exceptions.ForbiddenMoveException;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;

public class DebRemplissageEven extends RobotEven {

    public DebRemplissageEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
    }

    @Override
    public void execute() throws ForbiddenMoveException {
        robot.remplir();
    }
    
}
