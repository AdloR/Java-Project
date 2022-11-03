package simu.evenements;

public abstract class Evenement implements Comparable<Evenement> {
    private long date;
    private boolean auto;
    protected boolean priority;

    public Evenement(long date) {
        this.date = date;
        this.auto = true;
        this.priority = false;
    }

    public Evenement(long date, boolean auto) {
        this.date = date;
        this.auto = auto;
        this.priority = false;
    }

    public boolean isPriority() {
        return priority;
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
        int dateCmp = Long.compare(date, e.getDate());
        if (dateCmp == 0) {
            if (this.priority == e.isPriority())
                return 0;
            if (this.priority == true)
                return -1;
            if (e.isPriority() == true)
                return 1;
        }
        return dateCmp;
    }
}
