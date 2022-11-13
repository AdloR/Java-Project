package simu.evenements.robot_evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;
import terrain.Case;
import terrain.Direction;

/**
 * Makes a robot teleport toward given destination.
 */
public class RobotTeleportEven extends Evenement {
    int robotIndex;
    Simulateur sim;
    Direction dir;
    Robot robot;
    Case dest;

    /**
     * RobotTeleportEven constructor.
     * 
     * @param date       Date of planned teleportation.
     * @param sim        Running simulation.
     * @param robotIndex Robot id.
     * @param dest       tile (Case) where to telepot the robot.
     */
    public RobotTeleportEven(long date, Simulateur sim, int robotIndex, Case dest) {
        super(date);
        this.dest = dest;
        this.robotIndex = robotIndex;
        this.sim = sim;
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
        this.priority = true;
    }

    @Override
    public void execute() {
        try {
            robot.setPosition(dest);
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }

}
