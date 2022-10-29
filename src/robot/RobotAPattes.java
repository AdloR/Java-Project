package robot;

public class RobotAPattes extends Robot{
    public void RobotAChenille(Case position)
    {
        this.position = position;
        this.speed = 30;
        this.volumeIntervention = 10;
        this.timeIntervention = 1;
    }

    @override
    public int deverserEau(int vol)
    {
        return vol;
    }

    @override
    public void remplirReservoir() {}
}
