package exceptions;

public class NotNeighboringCasesException extends IllegalArgumentException {
    /**
     * Occur if Path.addStep is called on a case not neighboring start or end of the
     * path or if Carte.getDir is called with 2 Case not neighboring each other.
     *
     * @param message Message precising from which function the exception comes
     *
     * @exception NotNeighboringCasesException
     */

    public NotNeighboringCasesException(String message) {
        super(message);
    }

}
