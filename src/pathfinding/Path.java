package pathfinding;

import java.util.LinkedList;

import exceptions.NotNeighboringCasesException;
import terrain.Carte;
import terrain.Case;
import terrain.Direction;

/**
 * Represent a path with a LinkedList of Directions and the duration it will
 * take to the robot to follow it.
 */
public class Path {
    private Carte carte;
    private SelfDriving robot;
    private int duration;
    private Case start;
    private LinkedList<Direction> path;

    /**
     * Path Constructor
     * 
     * @param carte The Carte where the crossed Cases are.
     * @param robot The Robot that will follow the Path.
     * @param end   End of the Path.
     */
    public Path(Carte carte, SelfDriving robot, Case start) {
        this.carte = carte;
        this.robot = robot;
        this.duration = 0;
        this.start = start;
        this.path = new LinkedList<Direction>();
    }

    public int getDuration() {
        return duration;
    }

    public LinkedList<Direction> getPath() {
        return path;
    }

    /**
     * Add a step at the beginning of the path.
     * 
     * @param place A Case neighboring the start of the path to be added to the
     *              path.
     */
    public void addStep(Case place) throws NotNeighboringCasesException {
        /* Case place is neigboring start. */
        try {
            Direction dir = carte.getdir(place, start);
            path.addFirst(dir);
            duration += carte.getTailleCases() / robot.getSpeedOn(place);
            this.start = place;
            return;
        } catch (NotNeighboringCasesException e) {
        }
        /* Case place is somewhere else. */
        throw new NotNeighboringCasesException("thrown from Path.addStep");
    }

}
