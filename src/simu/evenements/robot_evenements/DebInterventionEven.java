package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Simulateur;

public class DebInterventionEven extends RobotEven {

    public DebInterventionEven(long date, Simulateur sim, int robotIndex) {
        super(date, sim, robotIndex);
    }


    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * 
     * @param date
     * @param sim
     * @param robot
     */
    public DebInterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
    }

    @Override
    public void execute() {
        robot.intervenir(getSim(), this.getDate());
    }

}
