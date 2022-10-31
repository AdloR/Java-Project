package exceptions;

public class UnknownDirectionException extends Exception{
    /**
     * Occur if you try to move to a direction not in {NORD, SUD, EST, OUEST}
     * @param message Message printed by caught exception
     */
    public UnknownDirectionException(String message) {
        super(message);
    }
}
