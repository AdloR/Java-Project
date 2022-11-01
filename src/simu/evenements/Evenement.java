package simu.evenements;

public abstract class Evenement implements Comparable<Evenement> {
    private long date;
    private boolean auto;

    public Evenement(long date) {
        this.date = date;
        this.auto = true;
    }

    public Evenement(long date, boolean auto) {
        this.date = date;
        this.auto = auto;
    }

    public long getDate() {
        return date;
    }

    public boolean isAuto() {
        return auto;
    }

    abstract public void execute();

    @Override
    public int compareTo(Evenement e) {
        return Long.compare(date, e.getDate());
    }
}
