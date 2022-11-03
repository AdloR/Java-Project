package simu.evenements.robot_evenements;

import robot.Robot;
import simu.Incendie;
import simu.Simulateur;

public class InterventionEven extends RobotEven {
    /**
     * Creates an event that asks to start an intervention.
     * 
     * It is an automatic event, meaning it is kept in history for restart.
     * It can only be automatic, someone wanting to program a scenario might want to
     * use DebInterventionEvent.
     * 
     * @param date       the date to execute the event
     * @param sim        the current Simulateur instance
     * @param robotIndex the index of the robot in DonneesSimulation::getRobots()
     *                   that will interevene.
     * 
     * @see DebInterventionEven
     */
    public InterventionEven(long date, Simulateur sim, Robot robot) {
        super(date, sim, robot);
    }

    @Override
    public void execute() {
        Incendie incendie = robot.getPosition().getIncendie();
        int used_water = robot.deverserEau();
        incendie.setNbL(incendie.getNbL() - used_water);

        if (incendie.getNbL() > 0 && robot.getReservoir() > 0) {
            getSim().ajouteEvenement(new DebInterventionEven(getSim().getDateSimulation() + 1, getSim(), robot));
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
