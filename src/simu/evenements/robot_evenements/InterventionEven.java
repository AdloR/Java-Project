package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Incendie;
import simu.Simulateur;

public class InterventionEven extends RobotEven {

    public InterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
        this.priority = true;
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);

        if (incendie.getNbL() > 0 && robot.getReservoir() > 0) {
            try {
                robot.intervenir(getSim(), this.getDate());
            } catch (IllegalStateException e) {
                e.printStackTrace();
                System.out.println(robot + ", classe : " + robot.getClass().getTypeName() + "@ ("
                        + robot.getPosition().getColonne() + ", " + robot.getPosition().getLigne() + ")");
            }
        }

        /* Redirect toward next task */
        // if (robot.getReservoir() <= 0) {
        // } else if (robot.getPosition().getIncendie().getNbL() > 0) {
        // try {
        // robot.intervenir(simu, simu.getDateSimulation() + 1);
        // } catch (IllegalStateException e) {

        // }
        // }
    }
}
