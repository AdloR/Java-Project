package pathfinding;

import java.util.LinkedList;

import exceptions.NotNeighboringCasesException;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

public class Path {
    private Carte carte;
    private SelfDriving robot;
    private int duration;
    private Case start;
    private Case end;
    private LinkedList<Direction> path;

    public Path(Carte carte, SelfDriving robot, Case origin) {
        this.carte = carte;
        this.robot = robot;
        this.duration = 0;
        this.start = origin;
        this.end = origin;
        this.path = new LinkedList<Direction>();
    }

    public int getDuration() {
        return duration;
    }

    public void addStep(Case place) throws NotNeighboringCasesException {
        try {
            Direction dir = carte.getdir(place, start);
            path.addFirst(dir);
            duration += carte.getTailleCases() / robot.getSpeedOn(place);
            this.start = place;
            return;
        } catch (NotNeighboringCasesException e) {
        }
        try {
            Direction dir = carte.getdir(end, place);
            path.addLast(dir);
            duration += carte.getTailleCases() / robot.getSpeedOn(end);
            this.end = place;
            return;
        } catch (NotNeighboringCasesException e) {
        }
        throw new NotNeighboringCasesException("thrown from Path.addStep");
    }

}
