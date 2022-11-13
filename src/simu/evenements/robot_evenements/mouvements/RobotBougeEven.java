package simu.evenements.robot_evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.Simulateur;
import simu.evenements.robot_evenements.RobotEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

/**
 * Makes the robot moves toward given direction. This event happen at the end of
 * the movement.
 */
public class RobotBougeEven extends RobotEven {
    Simulateur simu;

    Direction dir;

    /**
     * Creates an event for moving the robot.
     * 
     * It is a manual event, meaning it is kept in history for restart.
     * 
     * @param date       Date of the execution.
     * @param sim        Running simulation.
     * @param robotIndex The index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     * @param dir        Direction toward which to move the robot.
     */
    public RobotBougeEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
        this.priority = true;
    }

    /**
     * Creates an event for moving the robot.
     * 
     * It is an automatic event, meaning it is not kept in history for restart.
     * 
     * @param date  Date of the execution.
     * @param sim   Running simulation.
     * @param robot Robot to move.
     * @param dir   Direction toward which to move the robot.
     */
    public RobotBougeEven(long date, Simulateur sim, Robot robot, Direction dir) {
        super(date, sim, robot);
        this.dir = dir;
        this.priority = true;
    }

    @Override
    public void execute() {
        Case c = robot.getPosition();
        Carte carte = c.getCarte();

        try {
            robot.setPosition(carte.getVoisin(c, this.dir));
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }
}
