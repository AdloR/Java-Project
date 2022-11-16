package exceptions;

public class ForbiddenMoveException extends IllegalArgumentException {
    /**
     * Occur if you try to set the case of a robot to a case where the case nature
     * is
     * unreachable for the robot concerned.
     *
     * @param message Message printed by caught exception.
     */
    public ForbiddenMoveException(String message) {
        super(message);
    }
}
