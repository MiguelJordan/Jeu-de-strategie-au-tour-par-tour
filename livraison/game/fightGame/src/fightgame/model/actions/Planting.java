package fightgame.model.actions;

import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;


public class Planting extends AbstractAction {

    private Explosive plantedExplosive;
    private int position;

    public Planting (Personage performer, Explosive plantedExplosive, int position) {
        super(performer);
        this.plantedExplosive = plantedExplosive;
        this.position = position;
    }

    public Explosive getPlantedExplosive () {
        return this.plantedExplosive;
    }

    @Override
    public void getsExecuted () {
        this.performer.plant(this.plantedExplosive, this.position);
    } 

    @Override
    public String toString() {
       
        return "Planted By : " + performer.toString();
    }

}