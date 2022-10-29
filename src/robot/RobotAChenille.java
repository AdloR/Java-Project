package robot;

public class RobotAChenille extends Robot{
    public void RobotAChenille(Case position)
    {
        this.position = position;
        this.speed = 60;
        this.reservoirMax = 5000;
        this.reservoir = 0;
        this.timeRefill = 10 * 60;
        this.volumeIntervention = 100;
        this.timeIntervention = 8;
    }

    @override
    public void setSpeed(int speed)
    {
        assert(speed <= 80);
        super.setSpeed(speed);
    }
}
