package simu.evenements;

/**
 * Super class of all Evenements. It has some common properties for dealing with
 * Evenements.
 */
public abstract class Evenement implements Comparable<Evenement> {
    private long date;

    /**
     * True if the event is automatic
     * 
     * @see isAuto
     */
    private boolean auto;

    /**
     * Creates an Evenement that will execute at given date.
     * 
     * It is an automatic event, that WILL NOT be kept in history.
     * 
     * @param date date at which the Evenement will be executed
     */
    public Evenement(long date) {
        this.date = date;
        this.auto = true;
    }

    /**
     * Creates an Evenement that will execute at given date.
     * 
     * @param date date at which the Evenement will be executed
     * @param auto if true, this Evenement will be an automatic event, that WILL NOT
     *             be kept in history. if false, it WILL be kept.
     */
    public Evenement(long date, boolean auto) {
        this.date = date;
        this.auto = auto;
    }

    /**
     * Getter for date
     * 
     * @return the execution date
     */
    public long getDate() {
        return date;
    }

    /**
     * If auto is true, the Evenement is automatic and should not be kept in history
     * for restart. Otherwise, it is manual and should be kept.
     * 
     * @return if the event is automatic
     */
    public boolean isAuto() {
        return auto;
    }

    /**
     * Executes the Evenement. Should only be called by a Simulateur instance.
     */
    abstract public void execute();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Evenement e) {
        return Long.compare(date, e.getDate());
    }
}
