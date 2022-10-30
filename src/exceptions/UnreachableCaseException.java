package exceptions;

public class UnreachableCaseException extends Exception {
    /**
     * Occur if you try to go to an unreachable case from a start case.
     *
     * @param message Message printed by caught exception.
     *
     * @exception UnreachableCaseException
     */
    public UnreachableCaseException(String message) {
        super(message);
    }
}
