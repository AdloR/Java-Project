package simu.evenements.robot_evenements;

import simu.Simulateur;

public class DebRemplissageEven extends RobotEven {

    public DebRemplissageEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
    }

    @Override
    public void execute() {
        robot.remplir(getSim());
    }

}
