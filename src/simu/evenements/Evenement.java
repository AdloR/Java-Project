package simu.evenements;

public abstract class Evenement {
    private long date;

    public Evenement(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }

    abstract public void execute();
}
