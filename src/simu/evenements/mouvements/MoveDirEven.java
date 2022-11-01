package simu.evenements.mouvements;

import exceptions.ForbiddenMoveException;
import robot.Robot;
import simu.Simulateur;
import simu.evenements.Evenement;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public class MoveDirEven extends Evenement {
    Simulateur simu;
    Robot robot;
    Direction dir;

    public MoveDirEven(long date, Robot robot, Simulateur simu, Direction dir) {
        super(date);
        this.robot = robot;
        this.simu = simu;
        this.dir = dir;
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
