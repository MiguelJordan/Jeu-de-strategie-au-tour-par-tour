package fightgame.model.actions;

import gameplayers.entities.players.*;


public abstract class AbstractAction implements Action {

    protected Personage performer;

    public AbstractAction (Personage performer) {
        this.performer = performer;
    }

    @Override
    public Personage getPerformer () {
        return this.performer;
    }

}