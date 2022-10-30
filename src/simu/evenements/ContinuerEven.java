package simu.evenements;

import robot.Action;
import robot.Robot;

public class ContinuerEven extends Evenement {
    private Action action;
    private Robot robot;

    public ContinuerEven(long date, Robot robot, Action action) {
        super(date);
        this.action = action;
        this.robot = robot;
    }

    @Override
    public void execute() {
        switch (action) {
            case INTERVENTION:
                robot.intervenir();
                break;
            case REMPLISSAGE:
                robot.remplir();
                break;
            default:
                System.out.println("This action does not have to be repeated");
        }
    }

}
