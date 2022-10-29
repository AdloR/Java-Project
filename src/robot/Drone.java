package robot;

public class Drone extends Robot {
    public void Drone(Case case)
    {
        this.position = case;
        this.speed = 100;
        this.reservoirMax = 10000;
        this.reservoir = 0;
        this.timeRefill = 30 * 60;
        this.volumeIntervention = 10000;
        this.timeIntervention = 30;
    }

    @override
    public void setSpeed(int speed)
    {
        if (speed > 150)
        {
            throw new // TODO: throw an exception : Given speed out of bound, max 150.
        }
        super.setSpeed(speed);
    }
}