package simu.evenements.robot_evenements.mouvements;

import robot.Robot;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Direction;

/**
 * Order a robot to start a move toward given direction.
 */
public class DebRobotBougeEven extends RobotEven {
    Direction dir;

    /**
     * Creates an event for starting the robot's movement.
     * 
     * It is a manual event, meaning it is kept in history for restart.
     * 
     * @param date       Date of the execution.
     * @param sim        Running simulation.
     * @param robotIndex The index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     * @param dir        Direction toward which to move the robot.
     */
    public DebRobotBougeEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
    }

    /**
     * Creates an event for starting the robot's movement.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * 
     * @param date  Date of the execution.
     * @param sim   Running simulation.
     * @param robot Robot to move.
     * @param dir   Direction toward which to move the robot.
     */
    public DebRobotBougeEven(long date, Simulateur sim, Robot robot, Direction dir) {
        super(date, sim, robot);
        this.dir = dir;
    }

    @Override
    public void execute() {
        robot.startMove(getSim(), dir);
    }

}
