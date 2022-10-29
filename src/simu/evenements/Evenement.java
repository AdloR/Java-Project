package simu.evenements;

public abstract class Evenement implements Comparable<Evenement> {
    private long date;

    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    abstract public void execute();

    @Override
    public int compareTo(Evenement e) {
        return Long.compare(date, e.getDate());
    }
}
