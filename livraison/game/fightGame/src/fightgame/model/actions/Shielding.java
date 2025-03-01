package fightgame.model.actions;

import gameplayers.entities.players.*;


public class Shielding extends AbstractAction {

    public Shielding (Personage performer) {
        super(performer);
    }

    @Override
    public void getsExecuted () {
        this.performer.shieldUp();
    }

}