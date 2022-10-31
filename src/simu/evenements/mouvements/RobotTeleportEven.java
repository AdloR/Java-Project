package simu.evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;
import terrain.Case;
import terrain.Direction;

public class RobotTeleportEven extends Evenement {
    int robotIndex;
    Simulateur sim;
    Direction dir;
    Robot robot;
    Case dest;

    public RobotTeleportEven(long date, Simulateur sim, int robotIndex, Case dest) {
        super(date);
        this.dest = dest;
        this.robotIndex = robotIndex;
        this.sim = sim;
        this.robot = sim.getDonnees().getRobots().get(robotIndex);
    }

    @Override
    public void execute() throws ForbiddenMoveException {
        robot.setPosition(dest);
    }

}
