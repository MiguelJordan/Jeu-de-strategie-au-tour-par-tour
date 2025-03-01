package fightgame.model.actions;

import gameplayers.entities.players.*;
import gameplayers.entities.weapons.*;


public class Shooting extends AbstractAction {

    private Rifle usedGun;
    private int direction;

    public Shooting (Personage performer, Rifle usedGun, int direction) {
        super(performer);
        this.usedGun = usedGun;
        this.direction = direction;
    }

    public Rifle getUsedGun () {
        return this.usedGun;
    }

    @Override
    public void getsExecuted () {
        this.performer.shoot(this.usedGun, this.direction);
    }

}