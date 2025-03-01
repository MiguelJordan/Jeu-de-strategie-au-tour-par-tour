package fightgame.model.entities.collectables;

import gameplayers.entities.model.*;
import gameplayers.entities.players.*;
import gameplayers.util.geometry.*;


public class EnergyPellet extends AbstractEntity implements Collectable {

    private int value;

    public EnergyPellet (Position position, int value) {
        super(position);
        this.value = value;
    }

    @Override
    public void getsCollected (Personage collecter) {
        collecter.recoverHealth(this.value);
    }

}