package simu.evenements.mouvements;

import exceptions.ForbiddenMoveException;
import simu.Simulateur;
import simu.evenements.robot_evenements.ManRobotEven;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public class RobotBougeDirEven extends ManRobotEven {
    Direction dir;

    public RobotBougeDirEven(long date, Simulateur sim, int robotIndex, Direction dir) {
        super(date, sim, robotIndex);
        this.dir = dir;
    }

    @Override
    public void execute() {
        Case c = robot.getPosition();
        Carte carte = c.getCarte();

        try {
            robot.setPosition(carte.getVoisin(c, dir));
        } catch (ForbiddenMoveException e) {
            e.printStackTrace();
        }
    }

}
