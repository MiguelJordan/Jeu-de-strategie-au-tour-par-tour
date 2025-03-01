package fightgame.model.actions;

import gameplayers.entities.players.*;


public class Resting extends AbstractAction {

    public Resting (Personage performer) {
        super(performer);
    }

    @Override
    public void getsExecuted () {
        this.performer.rest();
    }

}