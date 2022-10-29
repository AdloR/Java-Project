package robot;

import terrain.Case;
import terrain.NatureTerrain;

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

    @Override
    public void setPosition(Case position)
    {
        if (position.getType() == NatureTerrain.EAU)
        {
            return;
        }
        super.setPosition(position);
}
