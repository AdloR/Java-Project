package robot;

import terrain.Case;

public class RobotAPattes extends Robot{
    public RobotAPattes(Case position)
    {
        this.position = position;
        this.speed = 30;
        this.volumeIntervention = 10;
        this.timeIntervention = 1;
    }

    @Override
    public int deverserEau(int vol)
    {
        return vol;
    }

    @Override
    public void remplirReservoir() {}
}
